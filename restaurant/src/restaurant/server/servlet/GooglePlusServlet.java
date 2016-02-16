package restaurant.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class GooglePlusServlet extends HttpServlet{

	private static final long serialVersionUID = -8453785206417514097L;
	
	  private static final String NETWORK_NAME = "Google";
	  
	  private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";
	 
	  private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";
	 
	  private static final Token EMPTY_TOKEN = null;
	  
	  private static final String apiKey = "1041612393341-odrlaea6k2b54m753c3j0q6m7232f1on.apps.googleusercontent.com";
	  private static final String apiSecret = "QifnBUGa3koMyXpEvUyQ24HF";
	  private static final String callbackUrl = "http://localhost:8080/restaurant/oauth2callback";
	
	  @Override 
	   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	      throws IOException, ServletException {
	      // Create OAuthService for Google OAuth 2.0
	      OAuthService service = new ServiceBuilder().provider(Google2Api.class)
	              .apiKey(apiKey).apiSecret(apiSecret).callback(callbackUrl)
	              .scope(SCOPE).build();
	 
	      Scanner in = new Scanner(System.in);
	 
	      System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
	      System.out.println();
	 
	      Verifier verifier = null;
	 
	      Token accessToken = null;
	 
	      // Obtain the Authorization URL
	      System.out.println("Fetching the Authorization URL...");
	      String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
	      System.out.println("Got the Authorization URL!");
	      System.out.println();
	      resp.sendRedirect(resp.encodeRedirectURL(authorizationUrl)); 
	      return;
	   }

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	} 
	  
	  
}
