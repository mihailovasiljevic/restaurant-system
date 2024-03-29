package restaurant.server.servlet.guests;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;

public class PrepareReservationServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4082852864440220852L;
	
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				System.out
						.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp
						.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			int restaurantId = -1;
			try{
				restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
			}catch(Exception ex){
				resp.sendRedirect(resp.encodeRedirectURL("../../guest/restaurants.jsp"));
			}
			
			if(restaurantId != -1){
				
				Restaurant rest = restaurantDao.findById(restaurantId);
				
				if(rest != null){
					req.getSession().setAttribute("restaurantForReservation", rest);
					resp.sendRedirect(resp.encodeRedirectURL("../../guest/setReservationTime.jsp"));
					return;
				}else{
					resp.sendRedirect(resp.encodeRedirectURL("../../guest/restaurants.jsp"));
				}
				
			}else{
				resp.sendRedirect(resp.encodeRedirectURL("../../guest/restaurants.jsp"));
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
