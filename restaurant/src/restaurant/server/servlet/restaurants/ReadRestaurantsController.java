package restaurant.server.servlet.restaurants;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.*;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.*;
import restaurant.server.session.RestaurantTypeDaoLocal;

public class ReadRestaurantsController extends HttpServlet {

	private static final long serialVersionUID = -6124263314361111772L;
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@EJB
	private StreetDaoLocal streetDao;

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
			
			List<Restaurant> restaurants = restaurantDao.findAll();
			req.getSession().setAttribute("restaurants", restaurants);

			List<RestaurantType> restaurantTypes = restaurantTypeDao.findAll();
			req.getSession().setAttribute("restaurantTypes", restaurantTypes);
			
			List<Street> streets = streetDao.findAll();
			req.getSession().setAttribute("streets", streets);
			
			resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurants.jsp"));
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
