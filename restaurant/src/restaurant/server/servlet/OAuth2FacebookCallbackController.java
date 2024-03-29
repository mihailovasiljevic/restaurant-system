package restaurant.server.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.externals.HashPassword;
import restaurant.server.entity.Image;
import restaurant.server.entity.User;
import restaurant.server.entity.UserType;
import restaurant.server.session.ImageDaoLocal;
import restaurant.server.session.UserDaoLocal;
import restaurant.server.session.UserTypeDaoLocal;

public class OAuth2FacebookCallbackController extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2634241380310655745L;

	private static final String NETWORK_NAME = "FACEBOOK";


	private static final Token EMPTY_TOKEN = null;

	  private static final String appId = "147298848985731";
	  private static final String appSecret = "d34e7f6be65f2db31f21edef0a050e96";
	  private static final String callbackUrl = "http://localhost:8080/restaurant/oauth2facebookcallback";
	  public static final String STATE = "state";
		@EJB
		private UserDaoLocal userDao;
		@EJB 
		private ImageDaoLocal imageDao;
		@EJB
		private UserTypeDaoLocal userTypeDao;
	  @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		OAuthService service = new ServiceBuilder()      
				  .apiKey(appId)
			      .apiSecret(appSecret)
			      .callback(callbackUrl)
			      .provider(FacebookApi.class)
			      .build();

		Scanner in = new Scanner(System.in);
		Token accessToken = null;
		Verifier verifier = null;
		String stateFromSession = (String) req.getSession().getAttribute(STATE);
		req.getSession().removeAttribute(STATE);

		verifier = new Verifier(req.getParameter("code"));
	    System.out.println();
		// Trade the Request Token and Verfier for the Access Token
		System.out.println("Trading the Request Token for an Access Token...");
		accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
		System.out.println("Got the Access Token!");
		System.out.println("(if your curious it looks like this: " + accessToken + " )");
		System.out.println();

		// Now let's go and ask for a protected resource!
		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");
		service.signRequest(accessToken, request);
		Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());

		System.out.println();
		System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
		in.close();
		ObjectMapper resultMapper = new ObjectMapper();
		String name = "UNKNOWN";
		String surname = "UKNOWN";
		String id = "";
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> data = mapper.readValue(response.getBody(), HashMap.class);
		for(String key : data.keySet()){
			if(key.equals("name")){
				name = new String(data.get(key));
				String[] values = name.split(" ");
				name = values[0];
				surname = values[1];
			}
			else if(key.equals("id"))
				id = data.get(key);

		}
		
		List<User> users = userDao.findAll();
		/*Proveriti da li je korisnik vec registrovan i ima nalog*/
		for(User u: users){
			if(HashPassword.isPassword(HashPassword.strToChar(id),u.getSalt(), u.getPassword())){
				req.getSession().setAttribute("user", u);
				resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
				return;
			}
		}
		/*Korisnik nema nalog, registruj ga*/
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setEmail("UNKNOWN"+id);
		byte[] salt = HashPassword.getNextSalt();
		byte[] hashedId = HashPassword.hashPassword(HashPassword.strToChar(id), salt);
		user.setSalt(salt);
		user.setPassword(hashedId);
		user.setActivated(true);
		user.setIsSessionActive(true);
		user.setAccountType("GLOBAL");
		
		List<UserType> userTypes = userTypeDao.findAll();
		
		for(UserType userType : userTypes){
			if(userType.getName().equals("GUEST")){
				user.setUserType(userType);
				userDao.persist(user);
				userDao.merge(user);
				userTypeDao.merge(userType);
				userType.add(user);
				userTypeDao.merge(userType);
				
			}
		}
		
		req.getSession().setAttribute("user", user);
		resp.sendRedirect(getServletContext().getContextPath()+"/guest/guest.jsp");
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	  
	  
}
