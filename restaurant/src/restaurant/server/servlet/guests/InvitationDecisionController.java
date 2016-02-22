package restaurant.server.servlet.guests;

import java.io.IOException;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Invitation;
import restaurant.server.entity.Reservation;
import restaurant.server.entity.User;
import restaurant.server.entity.Visit;
import restaurant.server.session.InvitationDaoLocal;
import restaurant.server.session.InvitationLocal;
import restaurant.server.session.ReservationDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTableDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;
import restaurant.server.session.UserDaoLocal;
import restaurant.server.session.VisitDaoLocal;

public class InvitationDecisionController extends HttpServlet{
	@EJB
	private TablesConfigurationDaoLocal tablesConfigurationDao;
	@EJB
	private ReservationDaoLocal reservatioDao;
	@EJB
	private RestaurantTableDaoLocal tableDao;
	@EJB
	private UserDaoLocal userDao;
	@EJB
	private InvitationLocal invitationMDB;
	@EJB
	private InvitationDaoLocal invitationDao;
	@EJB
	private VisitDaoLocal visitDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja za pristup ovoj stranici.");
				resp.sendRedirect(getServletContext().getContextPath()+"/index.jsp");
				return;
			}
			try{
				Boolean accept = Boolean.parseBoolean((req.getParameter("accept")));
				Reservation res =(Reservation) req.getSession().getAttribute("reservationForDecision");
				if(res == null){
					req.getSession().setAttribute("infoMessage", "Rezervacija kojoj pokusavate da pristupite ne postoji!");
					resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
					return;						
				}
				if(accept = true){
					
					Iterator<Invitation> it = res.getInvitations().iterator();
					while(it.hasNext()){
						Invitation inv = it.next();
						if(inv.getUserGuestInvitationReceived().getId().equals(user.getId())){
							if(inv.getInvitationAccepted() != null){
								req.getSession().setAttribute("infoMessage", "Vec ste prihvatili ili odbili poziv!");
								resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
								return;									
							}else{
								Visit reservationMakerVisit = new Visit();
								reservationMakerVisit.setReservation(res);
								reservationMakerVisit.setRestaurant(res.getRestaurant());
								reservationMakerVisit.setUser(user);
								reservationMakerVisit.setGrade(-1);
								visitDao.persist(reservationMakerVisit);
								user.add(reservationMakerVisit);
								res.getRestaurant().add(reservationMakerVisit);
								res.add(reservationMakerVisit);
								reservatioDao.merge(res);
								userDao.merge(user);
								restaurantDao.merge(res.getRestaurant());
								inv.setInvitationAccepted(true);
								invitationDao.merge(inv);
								resp.sendRedirect(getServletContext().getContextPath()+"/guest/myReservations.jsp");
				
								return;
							}
						}
					}

				}else{
					Iterator<Invitation> it = res.getInvitations().iterator();
					while(it.hasNext()){
						Invitation inv = it.next();
						if(inv.getUserGuestInvitationReceived().getId().equals(user.getId())){
							if(inv.getInvitationAccepted() != null){
								req.getSession().setAttribute("infoMessage", "Vec ste prihvatili ili odbili poziv!");
								resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
								return;									
							}else{
								inv.setInvitationAccepted(false);
								invitationDao.merge(inv);
								req.getSession().setAttribute("infoMessage", "Odbili ste da prisustvujete rezervaciji!");
								resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");	
								return;
							}
						}
					}				
				}
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
