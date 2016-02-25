package restaurant.server.servlet.restaurantTypes;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTypeDaoLocal;

public class ReadRestaurantTypesController extends HttpServlet {

	private static final long serialVersionUID = 6960690512239017210L;

	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if ((user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite trazenoj stranici.");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			List<RestaurantType> restaurantTypes = restaurantTypeDao.findAll();

			req.getSession().setAttribute("restaurantTypes", restaurantTypes);

			
			System.out.println("Iscitani tipovi!");
			if((user.getUserType().getName()).equals("SYSTEM_MENAGER")){
				resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/system-menager.jsp"));
			}
			else{
				resp.sendRedirect(resp.encodeRedirectURL("../../restaurant-menager/restaurantTypes.jsp"));
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
