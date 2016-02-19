package restaurant.server.servlet.dishes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Dish;
import restaurant.server.entity.User;
import restaurant.server.servlet.restaurants.RestaurantBean;
import restaurant.server.session.DishDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;

public class PrepareUpdateDishController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 802683107270866378L;
	@EJB
	private DishDaoLocal dishDao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("RESTAURANT_MENAGER")) {
				System.out
						.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp
						.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}

				int dishId = -1;
				try {
					ObjectMapper resultMapper = new ObjectMapper();
					ObjectMapper mapper = new ObjectMapper();
					HashMap<String, String> data = mapper.readValue(req.getParameter("dishId"), HashMap.class);
					for(String key : data.keySet()){
						if(key.equals("dishId"))
							dishId = Integer.parseInt(data.get(key));
					}
					Dish dish = dishDao.findById(dishId);
					if (dish != null) {
						req.getSession().setAttribute("updateDish",
								dish);

				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, new DishBean(dish.getName(), dish.getDescription(), dish.getPrice()));
						return;
					} else {					
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, "GRESKA");
						return;
						}
				} catch (Exception ex) {
					ObjectMapper resultMapper = new ObjectMapper();;
					
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "GRESKA");
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
