package restaurant.server.servlet;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

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
		resp.getWriter().write(response.getCode());
		resp.getWriter().write(response.getBody());
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	  
	  
}
