package restaurant.server.servlet.restaurants;

import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.*;
import restaurant.server.session.*;

public class DeleteRestaurantController extends HttpServlet{

	private static final long serialVersionUID = -3068822659842550680L;
	@EJB
	private RestaurantDaoLocal restaurantDao;
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
			if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try {
				int id = Integer.parseInt(req.getParameter("restaurantId"));
				Restaurant rest = restaurantDao.findById(id);
				if (rest != null) { // da nije neko u medjuvremno obrisao
					if (rest.getRestaurantMenagers().size() == 0 || rest.getTablesConfigurations().size() == 0 ||
							rest.getVisits().size() == 0) {
						
						
						restaurantDao.remove(rest);
						System.out.println("Brisanje: " + id + " uspelo.");
						
						
						
						resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
					}else{
						System.out.println("Brisanje: " + id + " nije moguce jer ima restorane vezane za sebe!");
						resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
					}
				} else {
					System.out.println("Neko obrisao u medjuvremenu.");
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
