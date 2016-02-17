package restaurant.server.servlet.restaurantMenagers;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Menu;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.MenuDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class ReadRestaurantMenagersController extends HttpServlet{

	private static final long serialVersionUID = 2934010243500997616L;

	@EJB 
	private UserDaoLocal userDao;
	
	@EJB
	private MenuDaoLocal menuDao;
	
	@EJB
	private TablesConfigurationDaoLocal tablesConfigurationDao;

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
				if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
					System.out
							.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
					resp.sendRedirect(resp
							.encodeRedirectURL("../../insufficient_privileges.jsp"));
					return;
				}
				
				List<User> restaurantMenagers = userDao.findRestaurantMenagerBySystemMenagerId(user.getId());
				req.getSession().setAttribute("restaurantMenagers", restaurantMenagers);


				
				resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurantMenagers.jsp"));
			}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}