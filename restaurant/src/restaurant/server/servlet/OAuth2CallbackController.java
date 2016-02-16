package restaurant.server.servlet;

import java.io.IOException;
import java.util.Scanner;

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

public class OAuth2CallbackController extends HttpServlet {

	private static final long serialVersionUID = 3351131772587828321L;
	private static final String NETWORK_NAME = "Google";

	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v2/userinfo?alt=json";

	private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

	private static final Token EMPTY_TOKEN = null;

	private static final String apiKey = "1041612393341-odrlaea6k2b54m753c3j0q6m7232f1on.apps.googleusercontent.com";
	private static final String apiSecret = "QifnBUGa3koMyXpEvUyQ24HF";
	private static final String callbackUrl = "http://localhost:8080/restaurant/oauth2callback";

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
		resp.getWriter().write(response.getCode());
		resp.getWriter().write(response.getBody());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
