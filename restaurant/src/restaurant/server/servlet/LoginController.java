package restaurant.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.externals.HashPassword;
import restaurant.externals.ResultCode;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class LoginController extends HttpServlet {

	private static final long serialVersionUID = -7345471861052209628L;

	private static Logger log = Logger.getLogger(LoginController.class);

	@EJB
	private UserDaoLocal userDao;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			/*If user is already logged in redirect him on adequate page.*/
			if(request.getSession().getAttribute("user") != null){
				User user = (User)request.getSession().getAttribute("user");
				
				switch(user.getUserType().getName()){
					case "GUEST": response.sendRedirect(response.encodeRedirectURL("../../system-menager/system-menager.jsp")); return;
					case "SYSTEM_MENAGER": response.sendRedirect(response.encodeRedirectURL("../../restaurantmenager/restaurant-menager.jsp")); return;
					case "RESTAURANT_MENAGER": response.sendRedirect(response.encodeRedirectURL("../../guest/guest.jsp")); return;
				}
				
			}
			ObjectMapper resultMapper = new ObjectMapper();
			String userEmail = "";
			String userPassword = "";
			String rememberMe = "";
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, String> data = mapper.readValue(request.getParameter("loginData"), HashMap.class);
			for(String key : data.keySet()){
				if(key.equals("userEmail"))
					userEmail = data.get(key);
				else if(key.equals("userPassword"))
					userPassword = data.get(key);
				else
					rememberMe = data.get(key);
			}

			
			if ((userEmail == null) || (userEmail.equals("")) || (userPassword == null) || (userPassword.equals(""))) {
		        response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        resultMapper.writeValue(out, ResultCode.LOGIN_EMPTY_FIELDS.toString());
				return;
			}
			
			
			User user = userDao.findUserByEmail(userEmail);
			
			if(user == null){
		        response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        resultMapper.writeValue(out, ResultCode.LOGIN_USER_DOESNT_EXISTS.toString());
				return;
			}else if(!user.getActivated()){
		        response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        resultMapper.writeValue(out, ResultCode.LOGIN_USER_NEEDS_ACTIVATION.toString());
				return;
			}
			else{
				if(!HashPassword.isPassword(HashPassword.strToChar(userPassword), user.getSalt(), user.getPassword())){
			        response.setContentType("application/json; charset=utf-8");
			        PrintWriter out = response.getWriter();
			        resultMapper.writeValue(out, ResultCode.LOGIN_USER_ERROR.toString());
					return;
				}else{
					HttpSession session = request.getSession(true);
					String sessionId = session.getId();
					Cookie sessionCookie = new Cookie("restaurant.session_id", sessionId);
					sessionCookie.setDomain(request.getServerName());
					sessionCookie.setPath(request.getContextPath());
					if(rememberMe.equals("1")){
						sessionCookie.setMaxAge(365 * 24 * 60 * 60);
					}
					response.addCookie(sessionCookie);
					user.setIsSessionActive(true);
					user.setSessionId(sessionId);
					userDao.merge(user);
					switch(user.getUserType().getName()){
						case "GUEST": 
							session.setAttribute("user", user);
					        response.setContentType("application/json; charset=utf-8");
					        PrintWriter out = response.getWriter();
					        resultMapper.writeValue(out, ResultCode.LOGIN_USER_SUCCESS_GUEST.toString());
							return;

						case "SYSTEM_MENAGER": 
							session.setAttribute("user", user);
					        response.setContentType("application/json; charset=utf-8");
					        PrintWriter out1 = response.getWriter();
					        resultMapper.writeValue(out1, ResultCode.LOGIN_USER_SUCCESS_SM.toString());
							return;

						case "RESTAURANT_MENAGER": 
							session.setAttribute("user", user);
					        response.setContentType("application/json; charset=utf-8");
					        PrintWriter out11 = response.getWriter();
					        resultMapper.writeValue(out11, ResultCode.LOGIN_USER_SUCCESS_RM.toString());
							return;
						
					}
				}
			}
		} catch (EJBException e) {
			if (e.getCause().getClass().equals(NoResultException.class)) {
				System.out.println("NEMA REZULTATA");
				response.sendRedirect(response.encodeRedirectURL("./login_error.jsp"));
				return;
			} else {
				System.out.println("BACEN EXCEPTION");
				throw e;
			}
		} catch (IOException e) {
			System.out.println("BACEN EXCEPTION");
			log.error(e);
			throw e;
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
