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

public class UpdateRestaurantTypeController extends HttpServlet {

	private static final long serialVersionUID = -8417302607924783795L;

	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;

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
			if ((user.getUserType().getName()).equals("GUEST")) {
				System.out
						.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp
						.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			if(req.getSession().getAttribute("updateRestaurantType") != null) {
				try {
					String name = null;
					ObjectMapper resultMapper = new ObjectMapper();
					ObjectMapper mapper = new ObjectMapper();
					HashMap<String, String> data = mapper.readValue(req.getParameter("restaurantTypeData"), HashMap.class);
					for(String key : data.keySet()){
						if(key.equals("typeName"))
							name = data.get(key);
					}
					
					if (name == null || name.equals("") || name.equals(" ")) {
						System.out.println("Polje je prazno!");
						resp.sendRedirect(resp
								.encodeRedirectURL("../../system-menager/restaurant-type/updateRestaurantType.jsp"));
						return;
					}
					RestaurantType rt = (RestaurantType) req.getSession()
							.getAttribute("updateRestaurantType");
					if (rt != null) {
						rt.setName(name);
						RestaurantType merged = restaurantTypeDao.merge(rt);
						if(merged != null){
					        resp.setContentType("application/json; charset=utf-8");
					        PrintWriter out = resp.getWriter();
					        resultMapper.writeValue(out, "USPEH");
						}else{
					        resp.setContentType("application/json; charset=utf-8");
					        PrintWriter out = resp.getWriter();
					        resultMapper.writeValue(out, "Nije uspelo azuriranje.");
						}
					} else {
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, "Nije uspelo azuriranje.");
					}
					removeSessionObject(req);
					return;

				} catch (IOException e) {
					ObjectMapper resultMapper = new ObjectMapper();;
					
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "Nije uspelo azuriranje");
			        removeSessionObject(req);
					return;
				}
			}else{
				ObjectMapper resultMapper = new ObjectMapper();;
		        resp.setContentType("application/json; charset=utf-8");
		        PrintWriter out = resp.getWriter();
		        resultMapper.writeValue(out, "Niste odabrali objekat za izmenu!");
		        removeSessionObject(req);
				return;
			}

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	private void removeSessionObject(HttpServletRequest req) {
		if (req.getSession().getAttribute("updateRestaurantType") != null) {
			req.getSession().removeAttribute("updateRestaurantType");
		}
	}

}
