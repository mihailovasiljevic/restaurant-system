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
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("RESTAURANT_MENAGER")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			
			String query = "SELECT k FROM Menu k WHERE k.userRestaurantMenager.id like '"+user.getId()+"'";
			List<Menu> menus = menuDao.findBy(query);
			req.getSession().setAttribute("menus", menus);
			
			query = "SELECT k FROM Restaurant k WHERE k.id like '"+user.getRestaurantMenagedBy().getId()+"'";
			List<Restaurant> restaurants = restaurantDao.findBy(query);
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
