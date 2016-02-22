package restaurant.server.servlet.guests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Invitation;
import restaurant.server.entity.Reservation;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.entity.Visit;
import restaurant.server.session.InvitationDaoLocal;
import restaurant.server.session.InvitationLocal;
import restaurant.server.session.ReservationDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTableDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;
import restaurant.server.session.UserDaoLocal;
import restaurant.server.session.VisitDaoLocal;

public class InviteFriendsController extends HttpServlet {
	@EJB
	private TablesConfigurationDaoLocal tablesConfigurationDao;
	@EJB
	private ReservationDaoLocal reservatioDao;
	@EJB
	private RestaurantTableDaoLocal tableDao;
	@EJB
	private UserDaoLocal userDao;
	@EJB
	private InvitationLocal invitationMDB;
	@EJB
	private InvitationDaoLocal invitationDao;
	@EJB
	private VisitDaoLocal visitDao;
	@EJB
	private RestaurantDaoLocal restaurantDao;
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
			if (!(user.getUserType().getName()).equals("GUEST")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			makeReservationFinal(req, resp, user);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	private synchronized void makeReservationFinal(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException{
		try {

			ObjectMapper resultMapper = new ObjectMapper();
			ObjectMapper mapper = new ObjectMapper();

			Restaurant restaurant = (Restaurant) req.getSession().getAttribute("restaurantForReservation");
			if (restaurant == null) {
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "Greska na sesiji. Pokusajte ponovo od prvog koraka rezervacije.");
				return;
			}

			HashMap<String, String> data = mapper.readValue(req.getParameter("tablesData"), HashMap.class);
			String checkedValues = "";
			int reservationForHowLong = -1;
			ArrayList<Integer> friendsIds = new ArrayList<>();
			System.out.println("");
			for (String key : data.keySet()) {
				if (key.equals("checkedValues")) {
					if (!data.get(key).equals(" ")) {
						String array = data.get(key).replaceAll("\\s+", "");
						String[] values = array.substring(0, array.length() - 1).split(",");
						for (String s : values) {
							if (!s.equals("null")) {
								friendsIds.add(Integer.parseInt(s));
							}
						}
					}
				}
			}

			ReservationBean rb = (ReservationBean) req.getSession().getAttribute("reservationInProgress");
			if (rb == null) {
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "Greska na sesiji. Pokusajte ponovo od prvog koraka rezervacije.");
				return;
			}

			TablesConfiguration conf = rb.getConf();
			String tableQuery = "SELECT k FROM RestaurantTable k WHERE k.tablesConfiguration.id like '"
					+ conf.getId() + "'";
			List<RestaurantTable> tables = tableDao.findBy(tableQuery);
			List<RestaurantTable> okTables = new ArrayList<>();
			
			okTables.addAll(tables);				//Get only tables that can be reserved at that time
				if(tables.size() > 0){
					Iterator<RestaurantTable> iter = okTables.iterator();

					while (iter.hasNext()) {
					    RestaurantTable tbl = iter.next();
						Calendar cal = Calendar.getInstance();
						cal.setTime(rb.getDate());
						int hourForReservation = cal.get(Calendar.HOUR_OF_DAY);
						int minuteForReservation = cal.get(Calendar.MINUTE);
						if(minuteForReservation > 30)
							hourForReservation++;
						
						for(Reservation res : tbl.getReservations()){
							if(res.getDate().compareTo(rb.getDate()) == 0){
								cal.setTime(res.getDate());
								int hourReserved = cal.get(Calendar.HOUR_OF_DAY);
								int minuteReserved = cal.get(Calendar.MINUTE);
								if (minuteReserved > 30)
									hourReserved++;
								boolean notOverlap = ((hourReserved <= (hourForReservation + reservationForHowLong))
										&& ((hourReserved + res.getForHowLong()) >= hourForReservation));
								if (!notOverlap) {
									iter.remove();
								}
							}
						}
					}
				}
			boolean reservationCanPass = true;
			ArrayList<RestaurantTable> tablesForReservation = new ArrayList<>();
			for(RestaurantTable id : rb.getListOfTables()){
				boolean passOne = false;
				for(RestaurantTable r : tables){
					if(r.getId().equals(id.getId())){
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


			if (reservationCanPass == false) {
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out,
						"Neki od stolova koje pokusavate da rezervisete je neko u medjuvremenu rezervisao.\nVratite se na pocetak i pokusaje ponovo.");
				return;
			}
			ArrayList<User> myFriends = new ArrayList<>();
			for(int i = 0; i < friendsIds.size(); i++){
				User friend = userDao.findById(friendsIds.get(i));
				if(friend != null){
					//eventualno proveri da li ga je ovaj drugi u medjuvremenu obrisao iz prijatelja!
					myFriends.add(friend);
				}
			}
			//konacno napravi rezervaciju
			
			Reservation reservation = new Reservation();
			reservation.setDate(rb.getDate());
			reservation.setRestaurant(restaurant);
			reservation.setForHowLong(rb.getForHowLong());
			if(reservation.getId() == null)
				reservation.setName("reservation0");
			else
				reservation.setName("reservation"+reservation.getId()+1);
			reservation.setGrade(-1);
			reservation.setUserGuestReservationMaker(user);
			Reservation perseistedReservation = reservatioDao.persist(reservation);
			
			if(perseistedReservation == null){
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "Nismo uspeli da napravim orezervaciju molimo vratite se na korak jedan.");
				return;
			}
			user.add(perseistedReservation);
			for(User u : myFriends){
				Invitation inv = new Invitation();
				if(inv.getId() == null)
					inv.setName("invitation0");
				else
					inv.setName("inv"+inv.getId()+1);
				inv.setReservation(perseistedReservation);
				inv.setUserGuestInvitationSender(user);
				inv.setUserGuestInvitationReceived(u);
				inv.setInvitationAccepted(null);
				Invitation persistedInv = invitationDao.persist(inv);
				if(persistedInv == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Nismo uspeli da napravimo rezervaciju molimo vratite se na korak jedan.");
					return;					
				}
				u.add(inv);
				user.add(inv);
				userDao.merge(u);
				userDao.merge(user);
				perseistedReservation.add(inv);
				reservatioDao.merge(perseistedReservation);
			}
			for(RestaurantTable r : rb.getListOfTables()){
				r.add(perseistedReservation);
				tableDao.merge(r);
				perseistedReservation.add(r);
				reservatioDao.merge(perseistedReservation);
			}

			Visit reservationMakerVisit = new Visit();
			reservationMakerVisit.setReservation(perseistedReservation);
			reservationMakerVisit.setRestaurant(restaurant);
			reservationMakerVisit.setUser(user);
			reservationMakerVisit.setGrade(-1);
			visitDao.persist(reservationMakerVisit);
			user.add(reservationMakerVisit);
			restaurant.add(reservationMakerVisit);
			perseistedReservation.add(reservationMakerVisit);
			reservatioDao.merge(perseistedReservation);
			userDao.merge(user);
			restaurantDao.merge(restaurant);
				try{
					HashMap<Integer, User> hm = new HashMap<>();
					hm.put(perseistedReservation.getId(), user);
					invitationMDB.invitate(hm);
				}
				catch(Exception ex){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Nismo uspeli da posaljemo poruke vasim prijateljima.");
					return;
				}
			if(req.getSession().getAttribute("reservationInProgress")!= null)
				req.getSession().removeAttribute("reservationInProgress");
			if(req.getSession().getAttribute("tables")!= null)
				req.getSession().removeAttribute("tables");				
			if(req.getSession().getAttribute("tablesConfiguration")!= null)
				req.getSession().removeAttribute("tablesConfiguration");				
			

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
