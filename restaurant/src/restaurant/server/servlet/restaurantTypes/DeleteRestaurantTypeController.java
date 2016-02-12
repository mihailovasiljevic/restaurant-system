package restaurant.server.servlet.restaurantTypes;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTypeDaoLocal;

public class DeleteRestaurantTypeController extends HttpServlet {

	private static final long serialVersionUID = -2426715324799267497L;

	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;

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
			try {
				int id = Integer.parseInt(req.getParameter("typeId"));
				RestaurantType rt = restaurantTypeDao.findById(id);
				if (rt != null) { // da nije neko u medjuvremno obrisao
					restaurantTypeDao.remove(rt);
					System.out.println("Brisanje: " + id + " uspelo.");
					resp.sendRedirect(resp
							.encodeRedirectURL("./restaurantTypes"));
				} else {
					System.out.println("Neko obrisao u medjuvremenu.");
					resp.sendRedirect(resp
							.encodeRedirectURL("./restaurantTypes"));
				}
			} catch (Exception ex) {
				System.out.println("Brisanje nije uspelo.");
				resp.sendRedirect(resp
						.encodeRedirectURL("./restaurantTypes"));
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
