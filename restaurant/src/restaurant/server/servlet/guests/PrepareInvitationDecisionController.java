package restaurant.server.servlet.guests;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try{
				Integer userId = Integer.parseInt(req.getParameter("userId"));
				Integer reservationId = Integer.parseInt(req.getParameter("reservation"));
				
				if(!userId.equals(user.getId())){
					System.out.println("Neko menjao get link id korisnika.");
					resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
					return;						
				}
				
				Reservation res = reservationDao.findById(reservationId);
				if(res == null){
					System.out.println("Nema vise te rezervacije nesto se desilo.");
					resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
					return;						
				}
				Date now = new Date();
				if(now.compareTo(res.getDate()) == 1){
					System.out.println("Proslo je vreme za prihvatanje te rezervacije. Rezervacija istekla.");
					resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
					return;						
				}else if(now.compareTo(res.getDate()) == 0){
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					int nowTime = cal.get(Calendar.HOUR_OF_DAY);
					cal.setTime(res.getDate());
					int resTime = cal.get(Calendar.HOUR_OF_DAY);
					
					if(nowTime-resTime >= 0){
						System.out.println("Proslo je vreme za prihvatanje te rezervacije. Rezervacija istekla.");
						resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
						return;	
					}
				}
				
				
				req.getSession().setAttribute("reservationForDecision", res);
				resp.sendRedirect(resp.encodeRedirectURL("../../guest/invitation.jsp"));
				return;	
			}catch(Exception ex){
				System.out.println("Neko menjao get link.");
				resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
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
