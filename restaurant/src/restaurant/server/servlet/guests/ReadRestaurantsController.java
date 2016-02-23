package restaurant.server.servlet.guests;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.User;
import restaurant.server.entity.Visit;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;

public class ReadRestaurantsController extends HttpServlet{
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
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
			if (!(user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			
			List<restaurant.server.entity.Restaurant> restaurants = restaurantDao.findAll();
			for(Restaurant r : restaurants){
				int noVisits = 0;
				int sumGrades = 0;
				double grade = 0.0;
				if(r.getVisits().size() > 0){
					Iterator<Visit> it = r.getVisits().iterator();
					while(it.hasNext()){
						Visit visit = it.next();
						if(visit.getGrade() != -1){
							noVisits++;
							sumGrades+=visit.getGrade();
						}
					}
				}
				if(noVisits > 0){
					grade = sumGrades/noVisits;
					r.setGrade(grade);
					restaurantDao.merge(r);
				}
			}
			req.getSession().setAttribute("restaurants", restaurants);

			List<RestaurantType> restaurantTypes = restaurantTypeDao.findAll();
			req.getSession().setAttribute("restaurantTypes", restaurantTypes);
			
			
			resp.sendRedirect(resp.encodeRedirectURL("../../guest/restaurants.jsp"));
			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}