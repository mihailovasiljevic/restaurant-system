package restaurant.server.servlet.restaurantMenagers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.externals.HashPassword;
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

public class CreateRestaurantMenagerController extends HttpServlet {

	private static final long serialVersionUID = -1085613434690860205L;

	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;

	@EJB
	private AddressDaoLocal addressDao;

	@EJB
	private UserTypeDaoLocal userTypeDao;

	@EJB
	private StreetDaoLocal streetDao;

	@EJB
	private UserDaoLocal userDao;

	@EJB
	private ImageDaoLocal imageDao;

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
				String name;
				String surname = null;
				String email = null;
				String password = null;
				int addressId;
				int streetId;
				String streetNo;

				name = req.getParameter("name");
				surname = req.getParameter("surname");
				email = req.getParameter("email");
				password = req.getParameter("password");
				streetNo = req.getParameter("addressNo");

				if (name == null || name.equals("") || name.equals(" ") || surname == null || surname.equals("")
						|| surname.equals(" ") || email == null || email.equals("") || email.equals(" ")
						|| password == null || password.equals("") || password.equals(" ")) {
					System.out.println("Obavezna polja su prazna!");
					resp.sendRedirect(resp
							.encodeRedirectURL("../../system-menager/restaurant-meanger/createRestaurantMenager.jsp"));
					return;
				}

				streetId = Integer.parseInt(req.getParameter("streetId"));
				addressId = Integer.parseInt(req.getParameter("addressId"));

				if (addressId != -1) {
					System.out.println("addressId: " + addressId);
					Address address = addressDao.findById(addressId);
					if (address == null) {
						System.out.println("Adresa obrisana u medjuvremenu");
						resp.sendRedirect(resp.encodeRedirectURL("./restaurantMenagers"));
						return;
					}

					persistUser(name, surname, email, password, req, address);

				} else {

					if (streetId == -1) {
						System.out.println("Nisu odabrani ni adresa ni ulica!");
						resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
						return;
					}
					if (streetNo == null || streetNo.equals("") || streetNo.equals(" ")) {
						System.out.println("Polje streetNo je prazno a izabrana je ulcia a nije izabrana adresa!");
						resp.sendRedirect(
								resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
						return;
					}
					Address address = new Address();
					Street street = streetDao.findById(streetId);
					if (street == null) {
						System.out.println("Obrisana ulica u medjuvremenu!");
						resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
						return;
					}
					address.setStreet(street);
					address.setBrojUUlici(streetNo);
					addressDao.persist(address);

					persistUser(name, surname, email, password, req, address);

				}

				resp.sendRedirect(resp.encodeRedirectURL("./restaurantMenagers"));
				return;

			} catch (IOException e) {
				System.out.println("Unos nije uspeo.");
				resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
				// throw e;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	private boolean persistUser(String name, String surname, String email, String password, HttpServletRequest req, Address address) {
		try {
			User restaurantMenager2 = new User();
			restaurantMenager2.setName(name);
			restaurantMenager2.setSurname(surname);
			restaurantMenager2.setEmail(email);
			restaurantMenager2.setActivated(true);
			List<UserType> userTypes = userTypeDao.findAll();
			for (UserType type : userTypes) {
				if (type.getName().equals("RESTAURANT_MENAGER")) {
					restaurantMenager2.setUserType(type);
					break;
				}
			}

			byte[] salt = new byte[16];
			byte[] hashedPassword = new byte[256];
			salt = HashPassword.getNextSalt();
			char[] passJan = HashPassword.strToChar(password);
			hashedPassword = HashPassword.hashPassword(passJan, salt);

			restaurantMenager2.setPassword(hashedPassword);
			restaurantMenager2.setSalt(salt);
			User user = (User) req.getSession().getAttribute("user");
			restaurantMenager2.setSystemMenager(user);
			restaurantMenager2.setAddress(address);

			String realName = (String) req.getSession().getAttribute("uploadImageRealName");
			if (realName != null) {
				String hashedName = (String) req.getSession().getAttribute("uploadImageHashedName");
				String path = (String) req.getSession().getAttribute("uploadImagePath");
				Image image = new Image();
				image.setName(hashedName);
				image.setPath(path);
				image.setRealName(realName);
				imageDao.persist(image);
				user.setImage(image);
			}

			userDao.persist(restaurantMenager2);

			user.add(restaurantMenager2);
			userDao.merge(user);

			if (realName != null) {
				restaurantMenager2.getImage().setUser(restaurantMenager2);
				imageDao.merge(restaurantMenager2.getImage());
			}
			return true;
		} catch (Exception ex) {
			return false;
		}

	}
}
