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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if ((user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			try {
				int id = Integer.parseInt(req.getParameter("typeId"));
				RestaurantType rt = restaurantTypeDao.findById(id);
				if (rt != null) { // da nije neko u medjuvremno obrisao
					if (rt.getRestaurants().size() == 0) {
						restaurantTypeDao.remove(rt);
						System.out.println("Brisanje: " + id + " uspelo.");
						req.getSession().setAttribute("infoMessage", "Brisanje uspelo!");
						resp.sendRedirect(resp.encodeRedirectURL("./restaurantTypes"));
					}else{
						System.out.println("Brisanje: " + id + " nije moguce jer ima restorane vezane za sebe!");
						req.getSession().setAttribute("infoMessage", "Brisanje nije moguce jer ima restorane vezane za sebe!");
						resp.sendRedirect(resp.encodeRedirectURL("./restaurantTypes"));
					}
				} else {
					System.out.println("Neko obrisao u medjuvremenu.");
					req.getSession().setAttribute("infoMessage", "Neko je verovatno obrisao ovo u medjuvremenu!");
					resp.sendRedirect(resp.encodeRedirectURL("./restaurantTypes"));
				}
			} catch (Exception ex) {
				System.out.println("Brisanje nije uspelo.");
				req.getSession().setAttribute("infoMessage", "Greska servera. Molimo pokusajte ponovo.!");
				resp.sendRedirect(resp.encodeRedirectURL("./restaurantTypes"));
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
