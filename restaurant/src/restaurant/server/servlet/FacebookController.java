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
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FacebookController extends HttpServlet{

	private static final long serialVersionUID = -9097476638547010491L;
	  public static final String STATE = "state";
	  private String applicationHost;
	  private OAuthService oAuthService;
	  // Jackson ObjectMapper
	  private ObjectMapper objectMapper;
	  
	  private static final String appId = "147298848985731";
	  private static final String appSecret = "d34e7f6be65f2db31f21edef0a050e96"; 
	  private static final String callbackUrl = "http://localhost:8080/restaurant/oauth2facebookcallback";

	  private static final Token EMPTY_TOKEN = null;
	  
	  
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
		 	 
		      System.out.println("=== FACEBOOK" + "'s OAuth Workflow ===");
		      System.out.println();
		 
		      Verifier verifier = null;
		 
		      Token accessToken = null;
		      String state = UUID.randomUUID().toString();
		      req.getSession().setAttribute(STATE, state);
		      // Obtain the Authorization URL
		      System.out.println("Fetching the Authorization URL...");
		      String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN) + "&" + STATE + "=" + state;
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
