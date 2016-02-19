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

import restaurant.externals.HashPassword;
import restaurant.externals.ResultCode;
import restaurant.server.entity.User;
import restaurant.server.session.AddressDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.StreetDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class SearchFriendsController extends HttpServlet{
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	
	@EJB
	private AddressDaoLocal addressDao;
	
	@EJB
	private StreetDaoLocal streetDao;
	
	@EJB
	private UserDaoLocal userDao;

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
				Integer searchFriendsBy = -1;
				String searchFriends = "";
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("searchData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("searchFriendsBy"))
						searchFriendsBy = Integer.parseInt(data.get(key));
					else if(key.equals("searchFriends"))
						searchFriends = data.get(key);
				}
				String searchBy = "";
				if(searchFriendsBy.equals(0)){
					searchBy = "name";
				}else{
					searchBy = "surname";
				}
				String query="SELECT k FROM User k WHERE k.userType.name like 'GUEST' and k."+searchBy+" like '%"+searchFriends+"%'";
				List<User> friends = userDao.findBy(query);
				if(friends != null){
					req.getSession().setAttribute("friends", friends);
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
				return;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4320730580809712511L;

}
