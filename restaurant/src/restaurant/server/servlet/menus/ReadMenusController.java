package restaurant.server.servlet.menus;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Menu;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.MenuDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;

public class ReadMenusController extends HttpServlet{

	private static final long serialVersionUID = 6829155029406423173L;
	@EJB
	private MenuDaoLocal menuDao;
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
			
			List<Menu> menus = menuDao.findAll();
			req.getSession().setAttribute("menus", menus);
			
			List<Restaurant> restaurants = restaurantDao.findAll();
			req.getSession().setAttribute("restaurants", restaurants);

			resp.sendRedirect(resp.encodeRedirectURL("../../restaurant-menager/menus.jsp"));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
