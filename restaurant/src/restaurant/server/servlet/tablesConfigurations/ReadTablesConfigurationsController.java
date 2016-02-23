package restaurant.server.servlet.tablesConfigurations;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTableDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;

public class ReadTablesConfigurationsController extends HttpServlet{

	private static final long serialVersionUID = -5305698648080660350L;
	
	@EJB
	private TablesConfigurationDaoLocal tablesConfigurationDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@EJB
	private RestaurantTableDaoLocal tableDao;
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
			String query = "SELECT k FROM TablesConfiguration k WHERE k.userRestaurantMenager.id like '"+user.getId()+"'";
			List<TablesConfiguration> configurations = tablesConfigurationDao.findBy(query);
			req.getSession().setAttribute("tablesConfigurations", configurations);
			
			query = "SELECT k FROM Restaurant k WHERE k.id like '"+user.getRestaurantMenagedBy().getId()+"'";
			List<Restaurant> restaurants = restaurantDao.findBy(query);
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
