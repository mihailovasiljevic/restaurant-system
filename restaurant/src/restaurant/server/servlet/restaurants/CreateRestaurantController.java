package restaurant.server.servlet.restaurants;

import java.io.IOException;
import java.util.Iterator;
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

public class CreateRestaurantController extends HttpServlet{

	private static final long serialVersionUID = -5436955086384864145L;
	
	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	
	@EJB
	private AddressDaoLocal addressDao;
	
	@EJB
	private RestaurantDaoLocal restaurantDao;
	
	@EJB
	private StreetDaoLocal streetDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		if(req.getSession().getAttribute("user") == null){
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		}else{
			User user = (User)req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if(!(user.getUserType().getName()).equals("SYSTEM_MENAGER")){
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
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
					resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
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
					Restaurant restaurant = new Restaurant();
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
					Restaurant persisted = restaurantDao.persist(restaurant);
					if(persisted == null){
						System.out.println("Nije se sacuvao entity!");
						resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
						return;	
					}
					
					address = addressDao.findById(addressId);
					address.add(persisted);
					rt = restaurantTypeDao.findById(typeId);
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
					if(street != null){
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
					Restaurant restaurant = new Restaurant();
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
					Restaurant persisted = restaurantDao.persist(restaurant);
					if(persisted == null){
						System.out.println("Nije se sacuvao entity!");
						resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
						return;	
					}
					try{
					address.add(restaurant);
					rt.add(restaurant);
					addressDao.merge(address);
					restaurantTypeDao.merge(rt);
					}catch(Exception ex){
						System.out.println("Exception");
					}
					Iterator<Restaurant> iteratora = address.getRestaurants().iterator();
					while(iteratora.hasNext()){
						System.out.println("U adresi: " + iteratora.next().getName());
					}
					
					Iterator<Restaurant> iteratorrt = rt.getRestaurants().iterator();
					while(iteratorrt.hasNext()){
						System.out.println("U tipu restorana: " + iteratorrt.next().getName());
					}
				}
				
				resp.sendRedirect(resp.encodeRedirectURL("./restaurants"));
				return;
				
			}catch (IOException e) {
				System.out.println("Unos nije uspeo.");
				resp.sendRedirect(resp.encodeRedirectURL("../../system-menager/restaurant/createRestaurant.jsp"));
				//throw e;
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	

}
