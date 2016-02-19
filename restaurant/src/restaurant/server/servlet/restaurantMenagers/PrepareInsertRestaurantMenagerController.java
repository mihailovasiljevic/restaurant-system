package restaurant.server.servlet.restaurantMenagers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Address;
import restaurant.server.entity.City;
import restaurant.server.entity.Country;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.User;
import restaurant.server.session.AddressDaoLocal;
import restaurant.server.session.CityDaoLocal;
import restaurant.server.session.CountryDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.StreetDaoLocal;

public class PrepareInsertRestaurantMenagerController extends HttpServlet{

	private static final long serialVersionUID = 5422577063434383516L;

	@EJB
	private StreetDaoLocal streetDao;

	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;

	@EJB
	private CountryDaoLocal countryDao;

	@EJB
	private CityDaoLocal cityDao;

	@EJB
	private AddressDaoLocal addressDao;

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
			if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}

			List<Street> streets = streetDao.findAll();
			List<RestaurantType> restaurantTypes = restaurantTypeDao.findAll();
			List<Country> countries = countryDao.findAll();
			List<City> cities = cityDao.findAll();
			try {
				List<Address> addresses = addressDao.findAll();
				req.getSession().setAttribute("addresses", addresses);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				List<Address> addresses= new ArrayList<>();
				Address ad1 = addressDao.findById(1);
				Address ad2 = addressDao.findById(2);
				Address ad3 = addressDao.findById(3);
				Address ad4 = addressDao.findById(4);
				Address ad5 = addressDao.findById(5);
				Address ad6 = addressDao.findById(6);
				Address ad7 = addressDao.findById(7);
				Address ad8 = addressDao.findById(8);
				Address ad9 = addressDao.findById(9);
				Address ad10 = addressDao.findById(10);
				addresses.add(ad1);
				addresses.add(ad2);
				req.getSession().setAttribute("addresses", addresses);
			}
			req.getSession().setAttribute("streets", streets);
			req.getSession().setAttribute("restaurantTypes", restaurantTypes);
			req.getSession().setAttribute("countries", countries);
			req.getSession().setAttribute("cities", cities);
			

			if (req.getParameter("restaurantId") == null) {
				System.out.println("Street + RestaurantType lists put on session.");
				resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant-menager/createRestaurantMenager.jsp"));
				return;
			} else {
				req.getRequestDispatcher("./updateRestaurantMenager").forward(req, resp);
				return;
			}
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	

}
