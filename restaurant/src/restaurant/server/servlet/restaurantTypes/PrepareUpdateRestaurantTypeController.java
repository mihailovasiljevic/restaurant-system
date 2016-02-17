package restaurant.server.servlet.restaurantTypes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTypeDaoLocal;

public class PrepareUpdateRestaurantTypeController extends HttpServlet{

	private static final long serialVersionUID = -7938746787762628789L;
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	
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
			if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
				System.out
						.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp
						.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}

				int typeId = -1;
				try {
					ObjectMapper resultMapper = new ObjectMapper();
					ObjectMapper mapper = new ObjectMapper();
					HashMap<String, String> data = mapper.readValue(req.getParameter("restaurantTypeId"), HashMap.class);
					for(String key : data.keySet()){
						if(key.equals("typeId"))
							typeId = Integer.parseInt(data.get(key));
					}
					RestaurantType rt = restaurantTypeDao.findById(typeId);
					if (rt != null) {
						req.getSession().setAttribute("updateRestaurantType",
								rt);
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, rt.getName());
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
