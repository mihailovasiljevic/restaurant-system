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
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if ((user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
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
				        resultMapper.writeValue(out, "Neko je verovanto obrisao tip entita koji pokusavate da izmenite. Osvezite stranicu.");
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
