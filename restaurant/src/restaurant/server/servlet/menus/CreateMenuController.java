package restaurant.server.servlet.menus;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Dish;
import restaurant.server.entity.Menu;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.DishDaoLocal;
import restaurant.server.session.MenuDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTableDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class CreateMenuController extends HttpServlet{


	private static final long serialVersionUID = 6705245883284761648L;
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@EJB 
	private UserDaoLocal userDao;
	@EJB
	private MenuDaoLocal menuDao;
	@EJB
	private DishDaoLocal dishDao;
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
			if (!(user.getUserType().getName()).equals("RESTAURANT_MENAGER")) {
				System.out.println("Korisnik nije menadzer resotrana i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try {

				ObjectMapper resultMapper = new ObjectMapper();
				String menuName = "";
				Date menuDateFrom = null;
				Date dateTo = null;
				Integer restaurantId = -1;
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("menuData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("menuName"))
						menuName = data.get(key);
					else if (key.equals("menuRestaurants"))
						restaurantId = Integer.parseInt(data.get(key));

					else if (key.equals("menuDateFrom")){
						String dateFromString = data.get(key);
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						dateFromString += " 12:00:00";
						try {
							menuDateFrom = sdf.parse(dateFromString);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "Datum nije ispravnog formata");
							e.printStackTrace();
							return;
						}
					}
				}

				if (menuDateFrom.equals("") || menuDateFrom == null || menuDateFrom.equals("") ) {
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Ime menija ne sme ostati prazno");
					return;
				}
				Restaurant rst = restaurantDao.findById(restaurantId);
				if(rst == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Neko je obrisao restoran u medjvuremenu.");
					return;
				}

				Menu menu = new Menu();
				menu.setCurrent(true);
				menu.setDateFrom(menuDateFrom);
				menu.setDateTo(null);
				menu.setName(menuName);
				menu.setRestaurant(rst);
				menu.setUserRestaurantMenager(user);
				Menu persisted = menuDao.persist(menu);
				if(persisted == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Nije uspelo cuvanje konfiguracije.");
					return;
				}

				user.add(persisted);
				userDao.merge(user);
				//postavi sve configuracije do sada na 
				if(rst.getMenus().size() > 0){
					Iterator<Menu> it = rst.getMenus().iterator();
					while(it.hasNext()){
						System.out.println(it.hasNext());
						if(it.next() != null)
							if(((Menu)it.next()).getDateTo() == null){
								((Menu)it.next()).setDateTo(new Date());
								((Menu)it.next()).setCurrent(!it.next().getCurrent());
								menuDao.merge(((Menu)it.next()));
							}
					}
					Menu  lastMenu = getLastElement(rst.getMenus());
					if(lastMenu.getDishes().size() > 0){
						Iterator<Dish> tablesIt = lastMenu.getDishes().iterator();
						while(tablesIt.hasNext()){
							if(tablesIt.next() != null){
								tablesIt.next().setMenu(persisted);
								dishDao.merge(tablesIt.next());
							}
						}
					}
				}
				
				rst.add(menu);
				restaurantDao.merge(rst);
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "USPEH");
				return;				
			}catch(Exception ex){
				
				ObjectMapper resultMapper = new ObjectMapper();
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, ex.getMessage());
				ex.printStackTrace();
				return;
			}
		}

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	private Menu getLastElement(Set<Menu> set) {
	    Iterator<Menu> itr = set.iterator();
	    Menu lastElement = itr.next();
	    while(itr.hasNext()) {
	        lastElement=itr.next();
	    }
	    return lastElement;
	}
}
