package restaurant.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class LoginController extends HttpServlet {

	private static final long serialVersionUID = -7345471861052209628L;

	private static Logger log = Logger.getLogger(LoginController.class);

	@EJB
	private UserDaoLocal userDao;
	

	@EJB
	private RestaurantTypeDaoLocal typeDao;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			
			String userEmail = request.getParameter("userEmail");
			String userPassword = request.getParameter("userPassword");
			
			if ((userEmail == null) || (userEmail.equals("")) || (userPassword == null) || (userPassword.equals(""))) {
				System.out.println("Neki ili svi parametri prazni.");
				response.sendRedirect(response.encodeRedirectURL("./login_error.jsp"));
				return;
			}
			
			User user = userDao.findUserByEmail(userEmail);
			
			
			
			if(user == null){
				System.out.println("ne postoji korisnik sa prosledjenom email adresom");
				response.sendRedirect(response.encodeRedirectURL("./login_error.jsp")); //ne postoji korisnik sa prosledjenom email adresom
				return;
			}else if(!user.getActivated()){
				System.out.println("Korisnicki nalog nije aktiviran");
				response.sendRedirect(response.encodeRedirectURL("./not_activated.jsp")); //nalog jos uvek nije aktiviran
				return;
			}
			else{
				if(!HashPassword.isPassword(HashPassword.strToChar(userPassword), user.getSalt(), user.getPassword())){
					System.out.println("email i password se ne poklapaju");
					response.sendRedirect(response.encodeRedirectURL("./login_error.jsp")); // email i password se ne poklapaju
					return;
				}else{
					HttpSession session = request.getSession(true);
					
					switch(user.getUserType().getName()){
						case "GUEST": 
							session.setAttribute("user", user);
							System.out.println("Korisnik " + user.getEmail() + " se prijavio.");
							response.sendRedirect(response.encodeRedirectURL("./guest/guest.jsp"));
							return;

						case "SYSTEM_MENAGER": 
							session.setAttribute("user", user);
							System.out.println("Korisnik " + user.getEmail() + " se prijavio.");
							List<RestaurantType> types = typeDao.findRestaurantTypeByUserId(user.getId());
							session.setAttribute("restaurantTypes", types);
							response.sendRedirect(response.encodeRedirectURL("./system-menager/system-menager.jsp"));
							return;

						case "RESTAURANT_MENAGER": 
							session.setAttribute("user", user);
							System.out.println("Korisnik " + user.getEmail() + " se prijavio.");
							response.sendRedirect(response.encodeRedirectURL("./restaurant-menager/restaurant-menager.jsp"));
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
