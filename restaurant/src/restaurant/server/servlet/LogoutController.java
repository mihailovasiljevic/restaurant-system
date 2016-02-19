package restaurant.server.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import restaurant.server.entity.User;
import restaurant.server.session.UserDaoLocal;

public class LogoutController extends HttpServlet{

	private static final long serialVersionUID = -4297456795067442971L;
	
	@EJB
	private UserDaoLocal userDao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 Cookie[] cookies = req.getCookies();
		 String sessionId = null;
		  if(cookies != null) {
		      for (int i = 0; i < cookies.length; i++) {
		          if(cookies[i].getName().equals("restaurant.session_id")){
		        	  sessionId = cookies[i].getValue();
		        	  break;
		       }
		   }
		   if(sessionId != null){
			   User user = (User)req.getSession().getAttribute("user");
				user.setIsSessionActive(false);
				userDao.merge(user);
				Cookie sessionCookie = new Cookie("restaurant.session_id", null);
				sessionCookie.setDomain(req.getServerName());
				sessionCookie.setPath(req.getContextPath());
				sessionCookie.setMaxAge(0);
				req.getSession().invalidate();
				resp.sendRedirect(resp.encodeRedirectURL("./index.jsp"));
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
