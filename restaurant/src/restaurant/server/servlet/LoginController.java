package restaurant.server.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import restaurant.externals.HashPassword;
import restaurant.server.entity.User;
import restaurant.server.session.UserDaoLocal;

public class LoginController extends HttpServlet {

	private static final long serialVersionUID = -7345471861052209628L;
	
	private static Logger log = Logger.getLogger(LoginController.class);

	@EJB
	private UserDaoLocal userDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			String userEmail = request.getParameter("userEmail");
			String userPassword = request.getParameter("userPassword");
			
			if ((userEmail == null) || (userEmail.equals("")) || (userPassword == null) || (userPassword.equals(""))) {
				response.sendRedirect(response.encodeRedirectURL("./login_error.jsp"));
				return;
			}
			
			User user = userDao.findUserByEmail(userEmail);
			
			if(user == null){
				response.sendRedirect(response.encodeRedirectURL("./login_error.jsp")); //ne postoji korisnik sa prosledjenom email adresom
				return;
			}else{
				if(!HashPassword.isPassword(HashPassword.strToChar(userPassword), user.getSalt(), user.getPassword())){
					response.sendRedirect(response.encodeRedirectURL("./login_error.jsp")); // email i password se poklapaju
					return;
				}else{
					HttpSession session = request.getSession(true);
					
					switch(user.getUserType().getName()){
						case "GUEST": 
							session.setAttribute("GUEST", user);
							log.info("Korisnik " + user.getEmail() + " se prijavio.");
							getServletContext().getRequestDispatcher("./guest").forward(request, response);
							return;

						case "SYSTEM_MENAGER": 
							session.setAttribute("SYSTEM_MENAGER", user);
							log.info("Korisnik " + user.getEmail() + " se prijavio.");
							getServletContext().getRequestDispatcher("./SystemMenagerServlet").forward(request, response);
							return;

						case "RESTAURANT_MENAGER": 
							session.setAttribute("RESTAURANT_MENAGER", user);
							log.info("Korisnik " + user.getEmail() + " se prijavio.");
							getServletContext().getRequestDispatcher("./restaurant-menager").forward(request, response);
							return;
						
					}
				}
			}
		} catch (EJBException e) {
			if (e.getCause().getClass().equals(NoResultException.class)) {
				response.sendRedirect(response.encodeRedirectURL("./login_error.jsp"));
			} else {
				throw e;
			}
		} catch (IOException e) {
			log.error(e);
			throw e;
		}
	}

	protected void doPost(HttpServletRequest request, 	HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
