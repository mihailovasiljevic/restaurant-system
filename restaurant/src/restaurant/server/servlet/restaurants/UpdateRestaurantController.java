package restaurant.server.servlet.restaurants;

import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.Address;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.User;
import restaurant.server.session.AddressDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.StreetDaoLocal;

public class UpdateRestaurantController extends HttpServlet{

	private static final long serialVersionUID = 3731062790778730566L;
	
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	
	@EJB
	private AddressDaoLocal addressDao;
	
	@EJB
	private RestaurantDaoLocal restaurantDao;
	
	@EJB
	private StreetDaoLocal streetDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			if (req.getSession().getAttribute("user") == null) {
				System.out.println("Nema korisnika na sesiji");
				resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
				return;
			} else {
				User user = (User) req.getSession().getAttribute("user");
				System.out.println("User type: " + user.getUserType().getName());
				if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
					System.out
							.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
					resp.sendRedirect(resp
							.encodeRedirectURL("../../insufficient_privileges.jsp"));
					return;
				}
				/**
				 * Update servlet ima dvojaku ulogu. Prvo proverava da li je na
				 * sesiji zakacen objekat za izmenu. Ako nije, kaci ga i salje se na
				 * jsp stranicu koja sadrzi formu za izmenu. Ako jeste, vrsi se
				 * izmena. Nakon obavljene izmene iz sesije se unistava objekat.
				 */
				if (req.getSession().getAttribute("updateRestaurant") == null) {
					int restaurantId;
					try {
						restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
						Restaurant rest = restaurantDao.findById(restaurantId);
						if (rest != null) {
							req.getSession().setAttribute("updateRestaurant",
									rest);
							System.out
									.println("Zakacen restoran za izmenu na sesiju.");
							resp.sendRedirect(resp
									.encodeRedirectURL("../../system-menager/restaurant/updateRestaurant.jsp"));
						} else {
							System.out
									.println("Ne postoji tip, mozda ga je neko obrisao u medjuvremenu.");
							resp.sendRedirect(resp
									.encodeRedirectURL("./restaurants"));
						}
					} catch (Exception ex) {
						System.out
								.println("Neko je mozda obrisao u medjuvremenu objekat.");
						resp.sendRedirect(resp
								.encodeRedirectURL("./restaurants"));
						return;
					}
				} else {
					try{
						String name;
						int typeId;
						int addressId;
						int streetId;
						String streetNo;
						
						name = req.getParameter("restaurantName");
						streetNo = req.getParameter("addressNo");
						
						if(name == null || name.equals("") || name.equals(" ")){
							System.out.println("Polje name je prazno!");
							resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/updateRestaurant.jsp"));
							return;
						}
						
						streetId = Integer.parseInt(req.getParameter("streetId"));
						addressId = Integer.parseInt(req.getParameter("addressId"));
						typeId = Integer.parseInt(req.getParameter("typeId"));
						
						if(addressId != -1){
							System.out.println("addressId: "+addressId);
							Address address = addressDao.findById(addressId);
							if(address == null){
								System.out.println("Adresa obrisana u medjuvremenu");
								resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
								return;
							}
							RestaurantType rt = restaurantTypeDao.findById(typeId);
							if(rt == null){
								System.out.println("Tip restorana obrisana u medjuvremenu");
								resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
								return;
							}			
							Restaurant restaurant = (Restaurant)req.getSession().getAttribute("updateRestaurant");
							restaurant.setAddress(address);
							restaurant.setRestaurantType(rt);
							restaurant.setName(name);
							if((User)req.getSession().getAttribute("user") != null)
								restaurant.setUserSystemMenager((User)req.getSession().getAttribute("user"));
							else{
								System.out.println("Pukla sesija u medjuvremenu");
								resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
								return;	
							}
							Restaurant persisted = restaurantDao.merge(restaurant);
							if(persisted == null){
								System.out.println("Nije se sacuvao entity!");
								resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
								return;	
							}
							
							address = addressDao.findById(addressId);
							address.add(persisted);
							rt = restaurantTypeDao.findById(typeId);
							HashSet<Restaurant> lista = (HashSet<Restaurant>) rt.getRestaurants();
							rt.add(persisted);
							addressDao.merge(address);
							restaurantTypeDao.merge(rt);
							
						}else{
							
							if(streetId == -1){
								System.out.println("Nisu odabrani ni adresa ni ulica!");
								resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
								return;	
							}
							if(streetNo == null || streetNo.equals("") || streetNo.equals(" ")){
								System.out.println("Polje streetNo je prazno a izabrana je ulcia a nije izabrana adresa!");
								resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
								return;
							}
							Address address = new Address();
							Street street = streetDao.findById(streetId);
							if(street == null){
								System.out.println("Obrisana ulica u medjuvremenu!");
								resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
								return;	
							}
							address.setStreet(street);
							address.setBrojUUlici(streetNo);
							addressDao.persist(address);
							
							RestaurantType rt = restaurantTypeDao.findById(typeId);
							if(rt == null){
								System.out.println("Tip restorana obrisana u medjuvremenu");
								resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
								return;
							}			
							Restaurant restaurant = (Restaurant)req.getSession().getAttribute("updateRestaurant");
							restaurant.setAddress(address);
							restaurant.setRestaurantType(rt);
							restaurant.setName(name);
							if((User)req.getSession().getAttribute("user") != null)
								restaurant.setUserSystemMenager((User)req.getSession().getAttribute("user"));
							else{
								System.out.println("Pukla sesija u medjuvremenu");
								resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
								return;	
							}
							Restaurant persisted = restaurantDao.merge(restaurant);
							if(persisted == null){
								System.out.println("Nije se sacuvao entity!");
								resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
								return;	
							}
							
							rt = restaurantTypeDao.findById(typeId);
							address.add(restaurant);
							rt.add(restaurant);
							addressDao.merge(address);
							restaurantTypeDao.merge(rt);

						}
						removeSessionObject(req);
						resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
						return;
				}catch (IOException e) {
					System.out.println("Izmena nije uspela.");
					resp.sendRedirect(resp
							.encodeRedirectURL("./restaurantTypes"));
					removeSessionObject(req);
					throw e;
				}
				}
			}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	private void removeSessionObject(HttpServletRequest req) {
		if (req.getSession().getAttribute("updateRestaurant") != null) {
			req.getSession().removeAttribute("updateRestaurant");
		}
	}
	
	


}
