package restaurant.server.servlet.restaurantMenagers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.*;

public class PrepareUpdateRestaurantMenagerController extends HttpServlet{
	@EJB
	private UserDaoLocal userDao;
	
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
			if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}

				int userId = -1;
				try {
					ObjectMapper resultMapper = new ObjectMapper();
					ObjectMapper mapper = new ObjectMapper();
					HashMap<String, String> data = mapper.readValue(req.getParameter("restaurantMenagerId"), HashMap.class);
					for(String key : data.keySet()){
						if(key.equals("userId"))
							userId = Integer.parseInt(data.get(key));
					}
					User restaurantMenager = userDao.findById(userId);
					if (restaurantMenager != null) {
						req.getSession().setAttribute("updateRestaurantMenager",
								restaurantMenager);
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        RestaurantMenager rm = null;
				        if(restaurantMenager.getImage() != null){
				        	rm = new RestaurantMenager(restaurantMenager.getName(), restaurantMenager.getSurname(),
				        		restaurantMenager.getEmail(), "", restaurantMenager.getImage().getRealName().replaceAll("\\s+",""));
				        }else{
					        rm = new RestaurantMenager(restaurantMenager.getName(), restaurantMenager.getSurname(),
					        		restaurantMenager.getEmail(), "", "");
				        }
				        
				        
				        resultMapper.writeValue(out, rm);
						return;
					} else {					
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, "Neko je verovanto obrisao menadzera kog pokusavate da izmenite. Osvezite stranicu.");
						return;
						}
				} catch (Exception ex) {
					ObjectMapper resultMapper = new ObjectMapper();;
					
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
