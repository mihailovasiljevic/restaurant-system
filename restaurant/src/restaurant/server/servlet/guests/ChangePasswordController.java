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
import restaurant.server.entity.Address;
import restaurant.server.entity.Street;
import restaurant.server.entity.User;
import restaurant.server.session.AddressDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.StreetDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class ChangePasswordController extends HttpServlet{

	private static final long serialVersionUID = -6276329500713310890L;
	

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
				String userNewPassword = "";
				String userOldPassword = "";
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("guestData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("userNewPassword"))
						userNewPassword = data.get(key);
					else if(key.equals("userOldPassword"))
						userOldPassword = data.get(key);
				}
				if (userNewPassword.equals("") || userNewPassword == null || userNewPassword.equals(" ") || 
						userOldPassword.equals("") || userOldPassword == null || userOldPassword.equals("")) {

					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_FIELD_EMPTY.toString());
					return;
				}
				byte[] salt;
				byte[] hashedPassword;
				if(!HashPassword.isPassword(HashPassword.strToChar(userOldPassword), user.getSalt(),user.getPassword())){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_FIELD_EMPTY.toString());
					return;					
				}
				
				salt = HashPassword.getNextSalt();
				hashedPassword = HashPassword.hashPassword(HashPassword.strToChar(userNewPassword), salt);
				user.setSalt(salt);
				user.setPassword(hashedPassword);
				User persisted = userDao.merge(user);
				if(persisted == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_FIELD_EMPTY.toString());
					return;
				}
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "USPEH");
				return;
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

}
