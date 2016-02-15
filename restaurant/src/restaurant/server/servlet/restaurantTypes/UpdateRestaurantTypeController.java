package restaurant.server.servlet.restaurantTypes;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTypeDaoLocal;

public class UpdateRestaurantTypeController extends HttpServlet {

	private static final long serialVersionUID = -8417302607924783795L;

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
			/**
			 * Update servlet ima dvojaku ulogu. Prvo proverava da li je na
			 * sesiji zakacen objekat za izmenu. Ako nije, kaci ga i salje se na
			 * jsp stranicu koja sadrzi formu za izmenu. Ako jeste, vrsi se
			 * izmena. Nakon obavljene izmene iz sesije se unistava objekat.
			 */
			if (req.getSession().getAttribute("updateRestaurantType") == null) {
				int typeId;
				try {
					typeId = Integer.parseInt(req.getParameter("typeId"));
					RestaurantType rt = restaurantTypeDao.findById(typeId);
					if (rt != null) {
						req.getSession().setAttribute("updateRestaurantType",
								rt);
						System.out
								.println("Zakacen restoran za izmenu na sesiju.");
						resp.sendRedirect(resp
								.encodeRedirectURL("../../system-menager/restaurant-type/updateRestaurantType.jsp"));
					} else {
						System.out
								.println("Ne postoji tip, mozda ga je neko obrisao u medjuvremenu.");
						resp.sendRedirect(resp
								.encodeRedirectURL("./restaurantTypes"));
					}
				} catch (Exception ex) {
					System.out
							.println("Neko je mozda obrisao u medjuvremenu objekat.");
					resp.sendRedirect(resp
							.encodeRedirectURL("./restaurantTypes"));
					return;
				}
			} else {
				try {
					String name;
					name = req.getParameter("typeName");
					Pattern whitespace = Pattern.compile("\\s*?");
					// Proveriti samo da li je nesto uneo u polje!
					if (name == null || name.equals("") || name.equals(" ")) {
						System.out.println("Polje je prazno!");
						resp.sendRedirect(resp
								.encodeRedirectURL("../../system-menager/restaurant-type/updateRestaurantType.jsp"));
						return;
					}
					RestaurantType rt = (RestaurantType) req.getSession()
							.getAttribute("updateRestaurantType");
					if (rt != null) {
						rt.setName(name);
						restaurantTypeDao.merge(rt);
						System.out.println("Izmenjen tip!");
						resp.sendRedirect(resp
								.encodeRedirectURL("./restaurantTypes"));
					} else {
						System.out
								.println("Ne postoji tip, mozda ga je neko obrisao u medjuvremenu.");
						resp.sendRedirect(resp
								.encodeRedirectURL("./restaurantTypes"));
					}
					removeSessionObject(req);
					return;

				} catch (IOException e) {
					System.out.println("Izmena nije uspela.");
					resp.sendRedirect(resp
							.encodeRedirectURL("./restaurantTypes"));
					removeSessionObject(req);
					throw e;
				}
			}

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	private void removeSessionObject(HttpServletRequest req) {
		if (req.getSession().getAttribute("updateRestaurantType") != null) {
			req.getSession().removeAttribute("updateRestaurantType");
		}
	}

}
