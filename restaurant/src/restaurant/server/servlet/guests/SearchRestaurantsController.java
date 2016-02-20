package restaurant.server.servlet.guests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantDaoLocal;

public class SearchRestaurantsController extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1913793682445149900L;
	@EJB
	private RestaurantDaoLocal restaurantDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try {

				ObjectMapper resultMapper = new ObjectMapper();
				Integer searchByType = -1;
				String searchByName = "";
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("searchData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("searchByType"))
						searchByType = Integer.parseInt(data.get(key));
					else if(key.equals("searchFriends"))
						searchByName = data.get(key);
				}
				String query = "";
				if(searchByType != -1){
					 query="SELECT k FROM Restaurant k WHERE k.restaurant.restaurantType.id like"+searchByType+" and k.restaurant.name like '%"+searchByName+"%'";
				}
				else{
					 query="SELECT k FROM Restaurant k WHERE k.restaurant.name like '%"+searchByName+"%'";
				}
				List<Restaurant> restaurants = restaurantDao.findBy(query);
				if(restaurants != null){
					req.getSession().setAttribute("restaurants", restaurants);
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "USPEH");
					return;
				}else{
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "GRESKA");
					return;
				}
				
			}catch(Exception ex){
				
				ObjectMapper resultMapper = new ObjectMapper();
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "GRESKA");
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
