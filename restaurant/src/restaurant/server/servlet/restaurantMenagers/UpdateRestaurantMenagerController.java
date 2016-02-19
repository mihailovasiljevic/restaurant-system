package restaurant.server.servlet.restaurantMenagers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.externals.HashPassword;
import restaurant.externals.ResultCode;
import restaurant.server.entity.Address;
import restaurant.server.entity.Image;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.User;
import restaurant.server.entity.UserType;
import restaurant.server.session.AddressDaoLocal;
import restaurant.server.session.ImageDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.StreetDaoLocal;
import restaurant.server.session.UserDaoLocal;
import restaurant.server.session.UserTypeDaoLocal;

public class UpdateRestaurantMenagerController extends HttpServlet {

	private static final long serialVersionUID = -1085613434690860205L;

	@EJB
	private UserDaoLocal userDao;
	@EJB
	private ImageDaoLocal imageDao;
	@EJB
	private UserTypeDaoLocal userTypeDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try {
				String uploadImageRealName = (String) req.getSession().getAttribute("uploadImageRealName");
				byte[] uploadImageHashedName = (byte[]) req.getSession().getAttribute("uploadImageHashedName");
				String uploadImagePath = (String) req.getSession().getAttribute("uploadImagePath");

				ObjectMapper resultMapper = new ObjectMapper();
				String userEmail = "";
				String userPassword = "";
				String userName = "";
				String userSurname = "";
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("registrationData"), HashMap.class);
				for (String key : data.keySet()) {
					if (key.equals("userEmail"))
						userEmail = data.get(key);
					else if (key.equals("userPassword"))
						userPassword = data.get(key);
					else if (key.equals("userName"))
						userName = data.get(key);
					else if (key.equals("userSurname"))
						userSurname = data.get(key);
				}

				if (userEmail.equals("") || userEmail == null 
						|| userName.equals("") || userName == null || userSurname.equals("") || userSurname == null) {

					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_FIELD_EMPTY.toString());
					if (uploadImageRealName != null) {
						File deleteFile = new File(uploadImagePath);
						// check if the file is present or not
						if (deleteFile.exists())
							deleteFile.delete();

						req.getSession().removeAttribute("uploadImageRealName");
						req.getSession().removeAttribute("uploadImageHashedName");
						req.getSession().removeAttribute("uploadImagePath");
					}
					return;
				}


				if (uploadImageRealName != null) {
					User rm = (User) req.getSession().getAttribute("updateRestaurantMenager");
					Image image;
					if(rm.getImage() != null){
						image = imageDao.findById(rm.getImage().getId());
						image.setName(uploadImageHashedName);
						image.setRealName(uploadImageRealName);
						image.setPath(uploadImagePath);
	
						imageDao.merge(image);
					}else{
						image = new Image();
						image.setName(uploadImageHashedName);
						image.setRealName(uploadImageRealName);
						image.setPath(uploadImagePath);
	
						imageDao.persist(image);
					}

					
					if (rm != null) {
						rm.setName(userName);
						rm.setSurname(userSurname);
						rm.setEmail(userEmail);
						if(userPassword != null || !userPassword.equals("")){
							byte[] salt = HashPassword.getNextSalt();
							byte[] hashedId = HashPassword.hashPassword(HashPassword.strToChar(userPassword), salt);
							rm.setSalt(salt);
							rm.setPassword(hashedId);
						}

						User persisted = userDao.merge(rm);

						if (persisted == null) {
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());

							if (uploadImageRealName != null) {
								File deleteFile = new File(uploadImagePath);
								// check if the file is present or not
								if (deleteFile.exists())
									deleteFile.delete();

								req.getSession().removeAttribute("uploadImageRealName");
								req.getSession().removeAttribute("uploadImageHashedName");
								req.getSession().removeAttribute("uploadImagePath");
							}

							return;
						}
						image.setUser(rm);
						imageDao.merge(image);
						rm.setImage(image);
						userDao.merge(rm);

					}
				} else {
					User rm = (User) req.getSession().getAttribute("updateRestaurantMenager");
					if (rm != null) {
						rm.setName(userName);
						rm.setSurname(userSurname);
						rm.setEmail(userEmail);
						if(!userPassword.equals("")){
							byte[] salt = HashPassword.getNextSalt();
							byte[] hashedId = HashPassword.hashPassword(HashPassword.strToChar(userPassword), salt);
							rm.setSalt(salt);
							rm.setPassword(hashedId);
						}

						User persisted = userDao.merge(rm);

						if (persisted == null) {
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());

							return;
						}

						userDao.merge(rm);

					}
				}
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "USPEH");

				if (uploadImageRealName != null) {
					req.getSession().removeAttribute("uploadImageRealName");
					req.getSession().removeAttribute("uploadImageHashedName");
					req.getSession().removeAttribute("uploadImagePath");
				}
				removeSessionObject(req);
				return;
			} catch (Exception ex) {
				String uploadImageRealName = (String) req.getSession().getAttribute("uploadImageRealName");
				byte[] uploadImageHashedName = (byte[]) req.getSession().getAttribute("uploadImageHashedName");
				String uploadImagePath = (String) req.getSession().getAttribute("uploadImagePath");
				if (uploadImageRealName != null) {
					File deleteFile = new File(uploadImagePath);
					// check if the file is present or not
					if (deleteFile.exists())
						deleteFile.delete();

					req.getSession().removeAttribute("uploadImageRealName");
					req.getSession().removeAttribute("uploadImageHashedName");
					req.getSession().removeAttribute("uploadImagePath");
				}
				ObjectMapper resultMapper = new ObjectMapper();
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "DOGODILA SE GRESKA");
				return;
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	private void removeSessionObject(HttpServletRequest req) {
		if (req.getSession().getAttribute("updateRestaurantMenager") != null) {
			req.getSession().removeAttribute("updateRestaurantMenager");
		}
	}
}
