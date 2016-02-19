package restaurant.server.servlet.restaurantTypes;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.externals.ResultCode;
import restaurant.externals.mapper.RestaurantTypeMapper;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class CreateRestaurantTypeController extends HttpServlet {
	
	private static final long serialVersionUID = 3709434783796975840L;
	private static Logger log = Logger.getLogger(CreateRestaurantTypeController.class);
	
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		if(req.getSession().getAttribute("user") == null){
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		}else{
			User user = (User)req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if(!(user.getUserType().getName()).equals("SYSTEM_MENAGER")){
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try{
				String name = null;
				int userId;
				ObjectMapper resultMapper = new ObjectMapper();
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("restaurantTypeData"), HashMap.class);
				for(String key : data.keySet()){
					if(key.equals("typeName"))
						name = data.get(key);
				}
				
				if(name == null || name.equals("") || name.equals(" ")){
					System.out.println("Polje je prazno!");
					resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant-type/createRestaurantType.jsp"));
					return;
				}
				
				RestaurantType rt = new RestaurantType();
				rt.setName(name);
				rt.setUserSystemMenager(user);
				
				restaurantTypeDao.persist(rt);
				
		        resp.setContentType("application/json; charset=utf-8");
		        PrintWriter out = resp.getWriter();
		        resultMapper.writeValue(out, "USPEH");
				return;
				
			}catch (IOException e) {
				ObjectMapper resultMapper = new ObjectMapper();
		        resp.setContentType("application/json; charset=utf-8");
		        PrintWriter out = resp.getWriter();
		        resultMapper.writeValue(out, "Nismo uspeli da sacuvamo tip restorana.");
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
	
	

}
