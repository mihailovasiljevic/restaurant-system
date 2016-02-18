package restaurant.server.servlet.tablesConfigurations;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class CreateTablesConfigurationController extends HttpServlet{

	
	private static final long serialVersionUID = -5173381013919473558L;
	
	@EJB
	private RestaurantDaoLocal restaurantDao;
	@EJB 
	private UserDaoLocal userDao;
	@EJB
	private TablesConfigurationDaoLocal tablesConfigurationDao;
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
				String confName = "";
				Date dateFrom = null;
				Date dateTo = null;
				int row = -1;
				int col = -1;
				Integer restaurantId = -1;
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("tablesConfigurationData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("confName"))
						confName = data.get(key);
					else if (key.equals("confRestaurants"))
						restaurantId = Integer.parseInt(data.get(key));
					else if (key.equals("confRow"))
						try{
							row = Integer.parseInt(data.get(key));
						}catch(Exception ex){
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "Za broj redova nije unet broj!");
							ex.printStackTrace();
							return;
						}
					else if (key.equals("confCol"))
						try{
							col = Integer.parseInt(data.get(key));
						}catch(Exception ex){
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "Za broj kolona nije unet broj!");
							ex.printStackTrace();
							return;
						}
					else if (key.equals("dateFrom")){
						String dateFromString = data.get(key);
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
						dateFromString += " 12:00:00";
						try {
							dateFrom = sdf.parse(dateFromString);
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

				if (confName.equals("") || confName == null || confName.equals("") ) {
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Ime konfiguracije ne sme ostati prazno");
					return;
				}
				Restaurant rst = restaurantDao.findById(restaurantId);
				if(rst == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Neko je obrisao restoran u medjvuremenu.");
					return;
				}
				TablesConfiguration conf = new TablesConfiguration();
				conf.setCurrent(true);
				HashSet<TablesConfiguration> configurations = (HashSet<TablesConfiguration>) rst.getTablesConfigurations();
				Iterator<TablesConfiguration> it = configurations.iterator();
				//postavi sve configuracije do sada na 
				if(configurations.size() > 0){
					while(it.hasNext()){
						it.next().setDateTo(new Date());
						tablesConfigurationDao.merge(it.next());
					}
				}
				
					Restaurant rst = new Restaurant();
					rst.setAddress(persistedAdr);
					rst.setGrade(-1);
					rst.setName(restaurantName);
					rst.setUserSystemMenager(user);
					rst.setRestaurantType(type);
					
					rst.setRestaurantType(type);
					if(menagers.size() > 0){
						for(int i = 0; i <menagers.size(); i++){
							User menager = userDao.findById((Integer)menagers.get(i));
							if(menager == null){
								resp.setContentType("application/json; charset=utf-8");
								PrintWriter out = resp.getWriter();
								resultMapper.writeValue(out, "GRESKA");
								return;
							}
							rst.add(menager);
							userDao.merge(menager);
						}
					}
					Restaurant persisted = restaurantDao.persist(rst);
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
					persistedAdr.add(rst);
					addressDao.merge(persistedAdr);
					
					type.add(persisted);
					restaurantTypeDao.merge(type);

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
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	

}
