package restaurant.server.servlet;
import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.Enumeration;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class RegistrationController extends HttpServlet{
	@EJB
	private UserDaoLocal userDao;
	@EJB
	private ImageDaoLocal imageDao;
	@EJB
	private UserTypeDaoLocal userTypeDao;
	@EJB
	private ActivateAccountLocal activateAccountDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper resultMapper = new ObjectMapper();
		String userEmail = "";
		String userPassword = "";
		String userName = "";
		String userSurname = "";
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> data = mapper.readValue(req.getParameter("registrationData"), HashMap.class);
		for(String key : data.keySet()){
			if(key.equals("userEmail"))
				userEmail = data.get(key);
			else if(key.equals("userPassword"))
				userPassword = data.get(key);
			else if(key.equals("userName"))
				userName = data.get(key);
			else if(key.equals("userSurname"))
				userSurname = data.get(key);			
		}
		
		if(userEmail.equals("") || userEmail == null || userPassword.equals("") || userPassword == null 
			|| userName.equals("") || userName == null || userSurname.equals("") || userSurname == null){
			
	        resp.setContentType("application/json; charset=utf-8");
	        PrintWriter out = resp.getWriter();
	        resultMapper.writeValue(out, ResultCode.REGISTER_USER_FIELD_EMPTY.toString());
			return;
		}
		
		List<User> users = userDao.findAll();
		/*Proveriti da li je korisnik vec registrovan i ima nalog*/
		for(User u: users){
			if(u.getEmail().equals(userEmail)){
		        resp.setContentType("application/json; charset=utf-8");
		        PrintWriter out = resp.getWriter();
		        resultMapper.writeValue(out, ResultCode.REGISTER_USER_ALREADY_EXISTS.toString());
				return;
			}
		}
		
		String uploadImageRealName = (String) req.getSession().getAttribute("uploadImageRealName");
		byte[] uploadImageHashedName = (byte[]) req.getSession().getAttribute("uploadImageHashedName");
		String uploadImagePath = (String) req.getSession().getAttribute("uploadImagePath");
		if(uploadImageRealName != null){
			Image image = new Image();
			image.setName(uploadImageHashedName);
			image.setRealName(uploadImageRealName);
			image.setPath(uploadImagePath);
			
			imageDao.persist(image);
					
			
			User user = new User();

			user.setName(userName);
			user.setSurname(userSurname);
			user.setEmail(userEmail);
			byte[] salt = HashPassword.getNextSalt();
			byte[] hashedId = HashPassword.hashPassword(HashPassword.strToChar(userPassword), salt);
			user.setSalt(salt);
			user.setPassword(hashedId);
			user.setActivated(false);
			user.setIsSessionActive(false);
			
			List<UserType> userTypes = userTypeDao.findAll();
			
			for(UserType userType : userTypes){
				if(userType.getName().equals("GUEST")){
					user.setUserType(userType);
					User persisted = userDao.persist(user);
					if(persisted == null){
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
						return;
					}
					image.setUser(user);
					imageDao.merge(image);
					user.setImage(image);

					byte[] tokenSalt = ByteBuffer.allocate(4).putInt(user.getId()).array();
					byte[] token = HashPassword.hashPassword(HashPassword.strToChar(user.getEmail()), tokenSalt);
					user.setToken(token);
					
					userDao.merge(user);
					userTypeDao.merge(userType);
					userType.add(user);
					userTypeDao.merge(userType);
			        activateAccountDao.activate(user);
			        break;
					
				}
			}
		}else{
			User user = new User();

			user.setName(userName);
			user.setSurname(userSurname);
			user.setEmail(userEmail);
			byte[] salt = HashPassword.getNextSalt();
			byte[] hashedId = HashPassword.hashPassword(HashPassword.strToChar(userPassword), salt);
			user.setSalt(salt);
			user.setPassword(hashedId);
			user.setActivated(false);
			user.setIsSessionActive(false);
			
			List<UserType> userTypes = userTypeDao.findAll();
			
			for(UserType userType : userTypes){
				if(userType.getName().equals("GUEST")){
					user.setUserType(userType);
					User persisted = userDao.persist(user);
					if(persisted == null){
				        resp.setContentType("application/json; charset=utf-8");
				        PrintWriter out = resp.getWriter();
				        resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
						return;
					}
					
					byte[] tokenSalt = ByteBuffer.allocate(4).putInt(user.getId()).array();
					byte[] token = HashPassword.hashPassword(HashPassword.strToChar(user.getEmail()), tokenSalt);
					user.setToken(token);
					
					userDao.merge(user);
					userTypeDao.merge(userType);
					userType.add(user);
					userTypeDao.merge(userType);
			        activateAccountDao.activate(user);
			        break;
					
				}
			}
		}
        resp.setContentType("application/json; charset=utf-8");
        req.setAttribute("email", userEmail);
        PrintWriter out = resp.getWriter();
        resultMapper.writeValue(out, ResultCode.REGISTER_USER_SUCCESS.toString());
		return;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
}
