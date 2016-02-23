package restaurant.server.servlet.guests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.entity.Visit;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.UserDaoLocal;
import restaurant.server.session.VisitDaoLocal;

public class GiveRestaurantAGradeController extends HttpServlet {
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@EJB
	private VisitDaoLocal visitDao;
	@EJB
	private UserDaoLocal userDao;
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
			if (!(user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			try {

				ObjectMapper resultMapper = new ObjectMapper();
				Integer grade = -1;
				Integer visitId = -1;
				String searchFriends = "";
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("gradeData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("grade"))
						grade = Integer.parseInt(data.get(key));
					else if (key.equals("visitId"))
						visitId = Integer.parseInt(data.get(key));
				}
				Visit vis = visitDao.findById(visitId);
				if (vis != null) {

					vis.setGrade(grade);
					visitDao.merge(vis);
					
					userDao.merge(user);
					
					int noVisits = 0;
					int sumGrades = 0;
					double gr = 0.0;
					if (vis.getRestaurant().getVisits().size() > 0) {
						Iterator<Visit> it = vis.getRestaurant().getVisits().iterator();
						while (it.hasNext()) {
							Visit visit = it.next();
							if (visit.getGrade() != -1) {
								noVisits++;
								sumGrades += visit.getGrade();
							}
						}
					}
					if (noVisits > 0) {
						gr = sumGrades / noVisits;
						vis.getRestaurant().setGrade( gr);
						restaurantDao.merge(vis.getRestaurant());
					}
				}

				List<restaurant.server.entity.Restaurant> restaurants = restaurantDao.findAll();
				req.getSession().setAttribute("restaurants", restaurants);

				List<RestaurantType> restaurantTypes = restaurantTypeDao.findAll();
				req.getSession().setAttribute("restaurantTypes", restaurantTypes);
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "USPEH");
				return;
			} catch (Exception ex) {
				ObjectMapper resultMapper = new ObjectMapper();
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "Greska na serveru. Molimo, pokusajte kasnije.");
				ex.printStackTrace();
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
