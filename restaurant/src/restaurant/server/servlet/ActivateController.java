package restaurant.server.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.externals.HashPassword;
import restaurant.externals.ResultCode;
import restaurant.server.session.*;
import restaurant.server.session.UserDaoLocal;
import restaurant.server.entity.*;

public class ActivateController extends HttpServlet{

	private static final long serialVersionUID = 3640011842487532509L;
	@EJB
	private UserDaoLocal userDao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer id;
		byte[] token;
		boolean activate;
		ObjectMapper mapper = new ObjectMapper();
		
		
		
		
		try{
			id = Integer.parseInt(req.getParameter("userId"));
			token = req.getParameter("token").getBytes();
			try{
				activate = Boolean.parseBoolean(req.getParameter("activate"));
				if(activate == false){
					System.out.println("Vrednost parametra activate mora biti TRUE!");
					req.setAttribute("ERROR_MESSAGE", "Vrednost parametra activate mora biti TRUE! ");
					resp.sendRedirect(resp.encodeRedirectURL("./activation_error.jsp"));
					return;
				}
				User user = userDao.findById(id);
				if(user.getActivated()){
					System.out.println("Nalog vec aktiviran");
					req.setAttribute("ERROR_MESSAGE", "Nalog vec aktiviran");
					resp.sendRedirect(resp.encodeRedirectURL("./activation_error.jsp"));
					return;
				}
				
				if(token.length != user.getToken().length){
					System.out.println("Tokeni se ne slazu po duzini.");
					req.setAttribute("ERROR_MESSAGE", "Tokeni se ne slazu po duzini.");
					resp.sendRedirect(resp.encodeRedirectURL("./activation_error.jsp"));
					return;					
				}
				
				for(int i = 0; i < token.length; i++){
					if(token[i] != user.getToken()[i]){
						System.out.println("Tokeni se ne slazu po sadrzaju.");
						req.setAttribute("ERROR_MESSAGE", "Tokeni se ne slazu po sadrzaju.");
						resp.sendRedirect(resp.encodeRedirectURL("./activation_error.jsp"));
						return;						
					}
				}
				
				user.setActivated(true);
				userDao.merge(user);
				
				System.out.println("Nalog uspesno aktiviran. ");
				req.setAttribute("SUCCESS_MESSAGE", "Uspesno ste aktivirali nalog.");
				resp.sendRedirect(resp.encodeRedirectURL("./guest/guest.jsp"));
				return;
			}catch(Exception ex){
				System.out.println("Pokusali ste da izmenite podatak o vrednosti aktivacije. ");
				req.setAttribute("ERROR_MESSAGE", "Pokusali ste da izmenite podatak o vrednosti aktivacije. ");
				resp.sendRedirect(resp.encodeRedirectURL("./activation_error.jsp"));
				return;
			}
		}catch(Exception ex){
			System.out.println("Pokusali ste da izmenite podatak o identifikacionom broju korisnika. ");
			req.setAttribute("ERROR_MESSAGE", "Pokusali ste da izmenite podatak o identifikacionom broju korisnika. ");
			resp.sendRedirect(resp.encodeRedirectURL("./activation_error.jsp"));
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	

}
