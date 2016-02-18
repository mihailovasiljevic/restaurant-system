package restaurant.server.servlet.tablesConfigurations;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;

public class ReadTablesConfigurationsController extends HttpServlet{

	private static final long serialVersionUID = -5305698648080660350L;
	
	@EJB
	private TablesConfigurationDaoLocal tablesConfigurationDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("RESTAURANT_MENAGER")) {
				System.out
						.println("Korisnik nije menadzer restorana i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp
						.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			
			List<TablesConfiguration> configurations = tablesConfigurationDao.findAll();
			req.getSession().setAttribute("tablesConfigurations", configurations);
			
			List<Restaurant> restaurants = restaurantDao.findAll();
			req.getSession().setAttribute("restaurants", restaurants);

			resp.sendRedirect(resp.encodeRedirectURL("../../restaurant-menager/restaurant-menager.jsp"));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
}
