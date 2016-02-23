package restaurant.server.servlet.dishes;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Dish;
import restaurant.server.entity.Menu;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.User;
import restaurant.server.session.DishDaoLocal;
import restaurant.server.session.MenuDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;

public class ReadDishesController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1177028753834059810L;
	@EJB
	private DishDaoLocal dishDao;

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
			
			
			String query = "SELECT k FROM Dish k WHERE k.menu.userRestaurantMenager.id like '"+user.getId()+"'";
			List<Dish> dishes = dishDao.findBy(query);
			req.getSession().setAttribute("dishes", dishes);
			


			resp.sendRedirect(resp.encodeRedirectURL("../../restaurant-menager/dishes.jsp"));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
