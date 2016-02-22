package restaurant.server.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Reservation;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.User;
import restaurant.server.session.ReservationDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class CleenUpServlet extends HttpServlet{
	@EJB
	private UserDaoLocal userDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@EJB
	private ReservationDaoLocal reservationDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<Restaurant> restaurants = restaurantDao.findAll();
		resp.getWriter().write("****RESTORANI*****");
		for(Restaurant r : restaurants){
			resp.getWriter().write(r.getName());
			resp.getWriter().write("****REZERVACIJA*****");
			restaurantDao.merge(r);
			resp.getWriter().write(r.getReservations().size());
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
}
