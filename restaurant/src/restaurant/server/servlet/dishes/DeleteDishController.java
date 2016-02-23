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
		try {
			int id = Integer.parseInt(req.getParameter("dishId"));
			Dish dish = dishDao.findById(id);
			if (dish != null) { // da nije neko u medjuvremno obrisao

					
					
				dishDao.remove(dish);
					System.out.println("Brisanje: " + id + " uspelo.");
					
					
					
					resp.sendRedirect(resp.encodeRedirectURL("./dishes"));
					
				}else{
					req.getSession().setAttribute("infoMessage", "Nismo uspeli da sacuvamo jelo. Molimo pokusajte ponovo.");
					resp.sendRedirect(resp.encodeRedirectURL("./dishes"));
				}
		} catch (Exception ex) {
			req.getSession().setAttribute("infoMessage", "Greska servera. Molimo pokusajte malo kasnije.");
			resp.sendRedirect(resp.encodeRedirectURL("./dishes"));
		}
	}

}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
