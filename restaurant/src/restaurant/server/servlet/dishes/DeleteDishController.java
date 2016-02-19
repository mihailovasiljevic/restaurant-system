package restaurant.server.servlet.dishes;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Dish;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.User;
import restaurant.server.session.DishDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;

public class DeleteDishController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6431176064466176865L;
	@EJB
	private DishDaoLocal dishDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
	if (req.getSession().getAttribute("user") == null) {
		System.out.println("Nema korisnika na sesiji");
		resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
		return;
	} else {
		User user = (User) req.getSession().getAttribute("user");
		System.out.println("User type: " + user.getUserType().getName());
		if (!(user.getUserType().getName()).equals("RESTAURANT_MENAGER")) {
			System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
			resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
			return;
		}
		try {
			int id = Integer.parseInt(req.getParameter("dishId"));
			Dish dish = dishDao.findById(id);
			if (dish != null) { // da nije neko u medjuvremno obrisao

					
					
				dishDao.remove(dish);
					System.out.println("Brisanje: " + id + " uspelo.");
					
					
					
					resp.sendRedirect(resp.encodeRedirectURL("./dishes"));
					
				}else{
					System.out.println("Brisanje: " + id + " nije moguce jer ima restorane vezane za sebe!");
					resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
				}
		} catch (Exception ex) {
			System.out.println("Brisanje nije uspelo.");
			resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
		}
	}

}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
