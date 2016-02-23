package restaurant.server.servlet.guests;

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
				String query="SELECT k FROM User k WHERE k.userType.name like 'GUEST' and k."+searchBy+" like '%"+searchFriends+"%' and k.id <> "+user.getId()+"";
				List<User> friends = userDao.findBy(query);
				ArrayList<User> myFriends = new ArrayList<>();
				ArrayList<User> notMyFriends = new ArrayList<>();
				if(friends != null){
					for(User u : friends){
						Iterator<User> it = user.getMyFriends().iterator();
						boolean found = false;
						while(it.hasNext()){
							User mu =it.next();
							if(u.getId().equals(mu.getId())){
								myFriends.add(u);
								found = true;
								break;
							}
						}
						if(found == false){
							notMyFriends.add(u);
						}
					}
					req.getSession().setAttribute("friends", myFriends);
					req.getSession().setAttribute("notFriends", notMyFriends);
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "USPEH");
					return;
				}else{
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Nismo uspeli da pronadjemo prijatelje. Greska na serveru. Pokusajte ponovo.");
					return;
				}
				
			}catch(Exception ex){
				
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 4320730580809712511L;

}
