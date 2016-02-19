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
			
			List<Dish> dishes = dishDao.findAll();
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
