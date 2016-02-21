package restaurant.server.servlet.guests;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Reservation;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.ReservationDaoLocal;
import restaurant.server.session.RestaurantTableDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;

public class ReservationPartTwoController extends HttpServlet{
	@EJB
	private TablesConfigurationDaoLocal tablesConfigurationDao;
	@EJB
	private ReservationDaoLocal reservatioDao;
	@EJB
	private RestaurantTableDaoLocal tableDao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				System.out
						.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp
						.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try {

				ObjectMapper resultMapper = new ObjectMapper();
				ObjectMapper mapper = new ObjectMapper();
				
				Restaurant restaurant = (Restaurant)req.getSession().getAttribute("restaurantForReservation");
				if(restaurant == null){
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "Greska na sesiji. Pokusajte ponovo od prvog koraka rezervacije.");
					return;
				}
				
				HashMap<String, String> data = mapper.readValue(req.getParameter("tablesData"), HashMap.class);
				String checkedValues = "";
				int reservationForHowLong=-1;
				ArrayList<Integer> tablesIds = new ArrayList<>();
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("checkedValues")){
						String array = data.get(key).replaceAll("\\s+","");
						String[] values = array.substring(0, array.length()-1).split(",");
						for(String s : values){
							if(!s.equals("null")){
								tablesIds.add(Integer.parseInt(s));
							}
						}
					}
				}
			   
				
				ReservationBean rb = (ReservationBean)req.getSession().getAttribute("reservationInProgress");
				if(rb == null){
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "Greska na sesiji. Pokusajte ponovo od prvog koraka rezervacije.");
					return;					
				}
				
				TablesConfiguration conf = rb.getConf();
				String tableQuery = "SELECT k FROM RestaurantTable k WHERE k.tablesConfiguration.id like '"+conf.getId()+"'";
				List<RestaurantTable> tables = tableDao.findBy(tableQuery);
				//Get only tables that can be reserved at that time
					if(tables.size() > 0){
						for(RestaurantTable r : tables){
							Calendar cal = Calendar.getInstance();
							cal.setTime(rb.getDate());
							int hourForReservation = cal.get(Calendar.HOUR_OF_DAY);
							int minuteForReservation = cal.get(Calendar.MINUTE);
							if(minuteForReservation > 30)
								hourForReservation++;
							
							for(Reservation res : r.getReservations()){
								if(res.getDate().compareTo(rb.getDate()) == 0){
									cal.setTime(res.getDate());
									int hourReserved = cal.get(Calendar.HOUR_OF_DAY);
									int minuteReserved = cal.get(Calendar.MINUTE);
									if (minuteReserved > 30)
										hourReserved++;
									boolean notOverlap = ((hourReserved <= (hourForReservation + reservationForHowLong))
											&& ((hourReserved + res.getForHowLong()) >= hourForReservation));
									if (!notOverlap) {
										tables.remove(r);
									}
								}
							}
						}
					}
				boolean reservationCanPass = true;
				ArrayList<RestaurantTable> tablesForReservation = new ArrayList<>();
				for(RestaurantTable r : tables){
					boolean passOne = false;
					for(Integer id : tablesIds){
						if(r.getId().equals(id)){
							passOne = true;
							tablesForReservation.add(r);
							break;
						}
					}
					if(passOne == false){
						reservationCanPass = false;
						break;
					}
				}
				
				if(reservationCanPass == false){
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "Neki od stolova koje pokusavate da rezervisete je neko u medjuvremenu rezervisao.\nVratite se na pocetak i pokusaje ponovo.");
					return;						
				}
				
				rb.setListOfTables(tablesForReservation);
				req.getSession().setAttribute("reservationInProgress", rb);
		        resp.setContentType("application/json; charset=utf-8");
		        PrintWriter out = resp.getWriter();
		        resultMapper.writeValue(out, "USPEH");
				return;				

			} catch (Exception ex) {
				ObjectMapper resultMapper = new ObjectMapper();
				ex.printStackTrace();
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
}
