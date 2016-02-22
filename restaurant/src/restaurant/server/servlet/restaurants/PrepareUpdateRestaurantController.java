package restaurant.server.servlet.restaurants;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.*;
import restaurant.server.session.*;

public class PrepareUpdateRestaurantController extends HttpServlet{

	private static final long serialVersionUID = -1045793106267431305L;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if ((user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}

				int restaurantId = -1;
				try {
					ObjectMapper resultMapper = new ObjectMapper();
					ObjectMapper mapper = new ObjectMapper();
					HashMap<String, String> data = mapper.readValue(req.getParameter("restaurantId"), HashMap.class);
					for(String key : data.keySet()){
						if(key.equals("restaurantId"))
							restaurantId = Integer.parseInt(data.get(key));
					}
					restaurant.server.entity.Restaurant rest = restaurantDao.findById(restaurantId);
					if (rest != null) {
						req.getSession().setAttribute("updateRestaurant",
								rest);
						List<Integer> menagers = new ArrayList<>();
						if(rest.getRestaurantMenagers().size() > 0){
							Iterator<User> it = rest.getRestaurantMenagers().iterator();
							while(it.hasNext()){
								menagers.add(it.next().getId());
							}
						}
						RestaurantBean restBean = new RestaurantBean(rest.getName(), rest.getRestaurantType().getId(),
								rest.getAddress().getStreet().getId(), rest.getAddress().getBrojUUlici(), menagers);
						
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, restBean);
						return;
					} else {					
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, "Neko je verovanto obrisao restoran koji pokusavate da izmenite. Osvezite stranicu.");
						return;
						}
				} catch (Exception ex) {
					ObjectMapper resultMapper = new ObjectMapper();;
					ex.printStackTrace();
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "Greska servera. Molimo pokusajte ponovo.");
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
