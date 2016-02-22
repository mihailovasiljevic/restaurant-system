package restaurant.server.servlet.guests;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Invitation;
import restaurant.server.entity.Reservation;
import restaurant.server.entity.User;
import restaurant.server.session.ReservationDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class PrepareInvitationDecisionController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8649232066056537289L;
	@EJB
	private ReservationDaoLocal reservationDao;
	@EJB
	private UserDaoLocal userDao;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			req.setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				req.setAttribute("infoMessage", "Nemate ovlascenja za pristup ovoj stranici.");
				resp.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
				return;
			}
			try{
				Integer userId = Integer.parseInt(req.getParameter("userId"));
				Integer reservationId = Integer.parseInt(req.getParameter("reservation"));
				
				if(!userId.equals(user.getId())){
					req.getSession().setAttribute("infoMessage", "Neispravan link.");
					resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
					return;						
				}
				
				Reservation res = reservationDao.findById(reservationId);
				if(res == null){
					req.getSession().setAttribute("infoMessage", "Rezervacija kojoj pokusavate da pristupite ne postoji!");
					resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
					return;						
				}
				
				Iterator<Invitation> it = res.getInvitations().iterator();
				
				while(it.hasNext()){
					Invitation inv = it.next();
					if(inv.getUserGuestInvitationReceived().getId().equals(user.getId())){
						if(inv.getInvitationAccepted() != null){
							req.getSession().setAttribute("infoMessage", "Vec ste prihvatili ili odbili poziv!");
							resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
							return;									
						}
					}
				}	
				
				Date now = new Date();
				if(now.compareTo(res.getDate()) == 1){
					req.getSession().setAttribute("infoMessage", "Proslo je vreme za prihvatanje te rezervacije. Rezervacija istekla.");
					resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
					return;						
				}else if(now.compareTo(res.getDate()) == 0){
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					int nowTime = cal.get(Calendar.HOUR_OF_DAY);
					cal.setTime(res.getDate());
					int resTime = cal.get(Calendar.HOUR_OF_DAY);
					
					if(nowTime-resTime >= 0){
						req.getSession().setAttribute("infoMessage", "Proslo je vreme za prihvatanje te rezervacije. Rezervacija istekla.");
						resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
						return;	
					}
				}
				
				req.getSession().setAttribute("reservationForDecision", res);
				resp.sendRedirect(getServletContext().getContextPath()+"/guest/invitation.jsp");

				return;	
			}catch(Exception ex){
				ex.printStackTrace();
				req.getSession().setAttribute("infoMessage", "Neispravan link.");
				resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
				return;				
			}
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
