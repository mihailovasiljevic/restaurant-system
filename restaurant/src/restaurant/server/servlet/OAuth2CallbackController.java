package restaurant.server.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
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
import restaurant.externals.*;
public class OAuth2CallbackController extends HttpServlet {

	private static final long serialVersionUID = 3351131772587828321L;
	private static final String NETWORK_NAME = "Google";

	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";

	private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

	private static final Token EMPTY_TOKEN = null;

	private static final String apiKey = "1041612393341-odrlaea6k2b54m753c3j0q6m7232f1on.apps.googleusercontent.com";
	private static final String apiSecret = "QifnBUGa3koMyXpEvUyQ24HF";
	private static final String callbackUrl = "http://localhost:8080/restaurant/oauth2callback";
	
	@EJB
	private UserDaoLocal userDao;
	@EJB 
	private ImageDaoLocal imageDao;
	@EJB
	private UserTypeDaoLocal userTypeDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Create OAuthService for Google OAuth 2.0
		OAuthService service = new ServiceBuilder().provider(Google2Api.class).apiKey(apiKey).apiSecret(apiSecret)
				.callback(callbackUrl).scope(SCOPE).build();

		Scanner in = new Scanner(System.in);
		Token accessToken = null;
		Verifier verifier = null;
		
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
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
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
		String googleId = "";
		String googleEmail = "";
		String googleName = "";
		String googleSurname = "";
		String picturePath = "";
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> data = mapper.readValue(response.getBody(), HashMap.class);
		for(String key : data.keySet()){
			if(key.equals("id"))
				googleId = data.get(key);
			else if(key.equals("email"))
				googleEmail = data.get(key);
			else if(key.equals("name"))
				googleName = data.get(key);
			else if(key.equals("family_name"))
				googleSurname = data.get(key);
			else if(key.equals("picture"))
				picturePath = data.get(key);
		}
		
		List<User> users = userDao.findAll();
		/*Proveriti da li je korisnik vec registrovan i ima nalog*/
		for(User u: users){
			if(HashPassword.isPassword(HashPassword.strToChar(googleId),u.getSalt(), u.getPassword())){
				req.getSession().setAttribute("user", u);
				resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
				return;
			}
		}
		/*Korisnik nema nalog, registruj ga*/
		Image image = new Image();
		String[] split = googleEmail.split("@");
		image.setRealName(split[0] + ".jpg");
		byte[] salt = HashPassword.getNextSalt();
		image.setName(HashPassword.hashPassword(HashPassword.strToChar(split[0] + ".jpg"), salt));
		image.setPath(picturePath);
		imageDao.persist(image);
		
		User user = new User();
		if(googleName.equals(""))
			googleName = "NEPOZNATO";
		if(googleSurname.equals(""))
			googleSurname = "NEPOZNATO";
		user.setName(googleName);
		user.setSurname(googleSurname);
		user.setEmail(googleEmail);
		salt = HashPassword.getNextSalt();
		byte[] hashedId = HashPassword.hashPassword(HashPassword.strToChar(googleId), salt);
		user.setSalt(salt);
		user.setPassword(hashedId);
		user.setActivated(true);
		user.setIsSessionActive(true);
		
		List<UserType> userTypes = userTypeDao.findAll();
		
		for(UserType userType : userTypes){
			if(userType.getName().equals("GUEST")){
				user.setUserType(userType);
				userDao.persist(user);
				image.setUser(user);
				imageDao.merge(image);
				user.setImage(image);
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
