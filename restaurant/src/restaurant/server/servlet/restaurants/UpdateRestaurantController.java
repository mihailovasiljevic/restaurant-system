package restaurant.server.servlet.restaurants;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.externals.ResultCode;
import restaurant.server.entity.Address;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.User;
import restaurant.server.session.AddressDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.*;

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
	
	@EJB
	private UserDaoLocal userDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if ((user.getUserType().getName()).equals("GUEST")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try {

				ObjectMapper resultMapper = new ObjectMapper();
				String restaurantName = "";
				Integer typeId = -1;
				Integer streetId = -1;
				String streetNo = "";
				ArrayList<Integer> menagers = new ArrayList<>();
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("restaurantData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("restaurantName"))
						restaurantName = data.get(key);
					else if (key.equals("restaurantType"))
						typeId = Integer.parseInt(data.get(key));
					else if (key.equals("street"))
						streetId = Integer.parseInt(data.get(key));
					else if (key.equals("streetNo"))
						streetNo = data.get(key);
					else if (key.equals("menagers")){
						String array = data.get(key).replaceAll("\\s+","");
						String[] values = array.substring(0, array.length()-1).split(",");
						for(String s : values){
							if(!s.equals("null")){
								menagers.add(Integer.parseInt(s));
							}
						}
					}
				}
				for(Integer i : menagers){
					if(i.equals(-1)){
						menagers.remove(i);
					}
				}
				if (streetNo.equals("") || streetNo == null || streetNo.equals("") || restaurantName == null
						|| restaurantName.equals("") || restaurantName == null || restaurantName.equals("") ) {

					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_FIELD_EMPTY.toString());
					return;
				}
				RestaurantType type = restaurantTypeDao.findById(typeId);
				if(type == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
					return;
				}
				Street street = streetDao.findById(streetId);
				if(street == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
					return;
				}				
				List<Address> addresses = addressDao.findAll();
				/* Proveriti da li vec postoji trazena adresa */
				for (Address a : addresses) {
					if (a.getBrojUUlici().equals(streetNo) && a.getStreet().getId().equals(streetId)) {
						Restaurant restaurant = (Restaurant)req.getSession().getAttribute("updateRestaurant");
						if(restaurant != null){
							restaurant.setAddress(a);
							restaurant.setName(restaurantName);
							restaurant.setUserSystemMenager(user);
							restaurant.setRestaurantType(type);
							if(menagers.size() > 0){
								for(int i = 0; i <menagers.size(); i++){
									User menager = userDao.findById((Integer)menagers.get(i));
									if(menager == null){
										resp.setContentType("application/json; charset=utf-8");
										PrintWriter out = resp.getWriter();
										resultMapper.writeValue(out, "GRESKA");
										return;
									}
									restaurant.add(menager);
									
								}
							}
							Restaurant persisted = restaurantDao.merge(restaurant);
							if(persisted == null){
								resp.setContentType("application/json; charset=utf-8");
								PrintWriter out = resp.getWriter();
								resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
								return;
							}
							
							for(int i = 0; i < menagers.size(); i++){
								User menager = userDao.findById((Integer)menagers.get(i));
								if(menager == null){
									resp.setContentType("application/json; charset=utf-8");
									PrintWriter out = resp.getWriter();
									resultMapper.writeValue(out, "GRESKA");
									return;
								}
								userDao.merge(menager);
							}
							
							a.add(restaurant);
							addressDao.merge(a);
							
							type.add(persisted);
							restaurantTypeDao.merge(type);
	
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "USPEH");
							removeSessionObject(req);
							return;
						}else{
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
							return;
						}
					}
				}
				
				Address adr = new Address();
				adr.setBrojUUlici(streetNo);
				adr.setStreet(street);
				Address persistedAdr = addressDao.persist(adr);
				if(persistedAdr == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
					return;
				}
				
					Restaurant restaurant = (Restaurant)req.getSession().getAttribute("updateRestaurant");
					if(restaurant != null){
						restaurant.setAddress(persistedAdr);
						restaurant.setName(restaurantName);
						restaurant.setUserSystemMenager(user);
						restaurant.setRestaurantType(type);

						if(menagers.size() > 0){
							for(int i = 0; i <menagers.size(); i++){
								User menager = userDao.findById((Integer)menagers.get(i));
								if(menager == null){
									resp.setContentType("application/json; charset=utf-8");
									PrintWriter out = resp.getWriter();
									resultMapper.writeValue(out, "GRESKA");
									return;
								}
								restaurant.add(menager);
								
							}
						}
						Restaurant persisted = restaurantDao.merge(restaurant);
						if(persisted == null){
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
							return;
						}
						
						for(int i = 0; i < menagers.size(); i++){
							User menager = userDao.findById((Integer)menagers.get(i));
							if(menager == null){
								resp.setContentType("application/json; charset=utf-8");
								PrintWriter out = resp.getWriter();
								resultMapper.writeValue(out, "GRESKA");
								return;
							}
							userDao.merge(menager);
						}
						persistedAdr.add(restaurant);
						addressDao.merge(persistedAdr);
						
						type.add(persisted);
						restaurantTypeDao.merge(type);
	
						resp.setContentType("application/json; charset=utf-8");
						PrintWriter out = resp.getWriter();
						resultMapper.writeValue(out, "USPEH");
						removeSessionObject(req);
						return;
					}else{
						resp.setContentType("application/json; charset=utf-8");
						PrintWriter out = resp.getWriter();
						resultMapper.writeValue(out, ResultCode.REGISTER_USER_ERROR.toString());
						return;
					}
			}catch(Exception ex){
				
				ObjectMapper resultMapper = new ObjectMapper();
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "GRESKA");
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
		if (req.getSession().getAttribute("updateRestaurant") != null) {
			req.getSession().removeAttribute("updateRestaurant");
		}
	}
	
	


}
