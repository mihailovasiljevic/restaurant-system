package restaurant.server.servlet.guests;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Reservation;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.servlet.restaurants.RestaurantBean;
import restaurant.server.session.ReservationDaoLocal;
import restaurant.server.session.RestaurantTableDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;

public class ReservationPartOneController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3247980496132253777L;

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
				
				HashMap<String, String> data = mapper.readValue(req.getParameter("reservationData"), HashMap.class);
				String reservationDate = "";
				String reservationTime = "";
				int reservationForHowLong=-1;
				
				for(String key : data.keySet()){
					if(key.equals("reservationDate"))
						reservationDate = data.get(key);
					else if(key.equals("reservationTime"))
						reservationTime = data.get(key);
					else if(key.equals("reservationForHowLong"))
						reservationForHowLong = Integer.parseInt(data.get(key));					
				}
				Date date;
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		    	String dateInString = reservationDate + " "+ reservationTime+":00";
				try {
					date = sdf.parse(dateInString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				String query="SELECT k FROM Reservation k WHERE k.restaurant.id like '"+restaurant.getId()+"'";
				List<Reservation> reservations = reservatioDao.findBy(query);
				
				String confQuery = "SELECT k FROM TablesConfiguration k WHERE k.restaurant.id like '"+restaurant.getId()+"'";
				List<TablesConfiguration> confs = tablesConfigurationDao.findBy(confQuery);
				
				String tableQuery = "SELECT k FROM RestaurantTable k WHERE k.tablesConfiguration.id like '"+confs.get(confs.size()-1).getId()+"'";
				List<RestaurantTable> tablesFromRestaurant = tableDao.findBy(tableQuery);
				
				
				if(reservations != null){
					if(reservations.size() > 0){
						for(Reservation r: reservations){
							if(r.getDate().compareTo(date) == 0){
								Calendar cal = Calendar.getInstance();
								cal.setTime(date);
								int hourForReservation = cal.get(Calendar.HOUR_OF_DAY);
								int minuteForReservation = cal.get(Calendar.MINUTE);
								if(minuteForReservation > 30)
									hourForReservation++;
								
								cal.setTime(r.getDate());
								int hourReserved = cal.get(Calendar.HOUR_OF_DAY);
								int minuteReserved = cal.get(Calendar.MINUTE);
								if(minuteReserved > 30)
									hourReserved++;			
								boolean notOverlap = ((hourReserved<=(hourForReservation + reservationForHowLong)) && ((hourReserved+r.getForHowLong())>=hourForReservation));
								if(!notOverlap){
							        resp.setContentType("application/json; charset=utf-8");
							        PrintWriter out = resp.getWriter();
							        resultMapper.writeValue(out, "Na zadati dan termin je zauzet.");
									return;
								}
							}
						}
					}
					if(tablesFromRestaurant.size() > 0){
						for(RestaurantTable r : tablesFromRestaurant){
							Calendar cal = Calendar.getInstance();
							cal.setTime(date);
							int hourForReservation = cal.get(Calendar.HOUR_OF_DAY);
							int minuteForReservation = cal.get(Calendar.MINUTE);
							if(minuteForReservation > 30)
								hourForReservation++;
							
							for(Reservation res : r.getReservations()){
								if(res.getDate().compareTo(date) == 0){
									cal.setTime(res.getDate());
									int hourReserved = cal.get(Calendar.HOUR_OF_DAY);
									int minuteReserved = cal.get(Calendar.MINUTE);
									if (minuteReserved > 30)
										hourReserved++;
									boolean notOverlap = ((hourReserved <= (hourForReservation + reservationForHowLong))
											&& ((hourReserved + res.getForHowLong()) >= hourForReservation));
									if (!notOverlap) {
										tablesFromRestaurant.remove(r);
									}
								}
							}
						}
					}
					ReservationBean reservationBean = new ReservationBean();
					reservationBean.setDate(date);
					reservationBean.setForHowLong(reservationForHowLong);
					reservationBean.setConf(confs.get(confs.size()-1));
					reservationBean.setListOfTables(tablesFromRestaurant);
					req.getSession().setAttribute("reservationInProgress", reservationBean);
					req.getSession().setAttribute("tables", tablesFromRestaurant);
					req.getSession().setAttribute("tablesConfiguration", confs.get(confs.size()-1));
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "USPEH");
					return;
				}else{
					ReservationBean reservationBean = new ReservationBean();
					reservationBean.setDate(date);
					reservationBean.setForHowLong(reservationForHowLong);
					reservationBean.setConf(confs.get(confs.size()-1));
					reservationBean.setListOfTables(tablesFromRestaurant);
					req.getSession().setAttribute("reservationInProgress", reservationBean);
					req.getSession().setAttribute("tables", tablesFromRestaurant);
					req.getSession().setAttribute("tablesConfiguration", confs.get(confs.size()-1));
			        resp.setContentType("application/json; charset=utf-8");
			        PrintWriter out = resp.getWriter();
			        resultMapper.writeValue(out, "USPEH");
					return;
				}
				

			} catch (Exception ex) {
				ObjectMapper resultMapper = new ObjectMapper();;
				
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
