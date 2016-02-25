package restaurant.server.servlet.guests;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
import restaurant.server.entity.User;
import restaurant.server.session.AddressDaoLocal;
import restaurant.server.session.RestaurantDaoLocal;
import restaurant.server.session.RestaurantTypeDaoLocal;
import restaurant.server.session.StreetDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class UpdateGuestController extends HttpServlet{

	private static final long serialVersionUID = -8987953916956074826L;

	@EJB
	private RestaurantTypeDaoLocal restaurantTypeDao;
	
	@EJB
	private AddressDaoLocal addressDao;
	
	@EJB
	private StreetDaoLocal streetDao;
	
	@EJB
	private UserDaoLocal userDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (req.getSession().getAttribute("user") == null) {
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			try {

				ObjectMapper resultMapper = new ObjectMapper();
				String userName = "";
				String userSurname = "";
				Integer streetId = -1;
				String streetNo = "";
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("guestData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("userName"))
						userName = data.get(key);
					else if (key.equals("userSurname"))
						userSurname = data.get(key);
					else if (key.equals("userStreet"))
						streetId = Integer.parseInt(data.get(key));
					else if(key.equals("userStreetNo"))
						streetNo = data.get(key);
				}
				if (streetNo.equals("") || streetNo == null || streetNo.equals("") || userName == null
						|| userName.equals("") || userName == null || userSurname.equals("") ||
						userSurname.equals(" ") || userSurname == null) {

					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Morate da popunite sva polja!");
					return;
				}
				Street street = streetDao.findById(streetId);
				if(street == null){
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Nismo uspeli da sacuvamo ulicu koju ste zadali. Molimo pokusajte ponovo.");
					return;
				}				
				List<Address> addresses = addressDao.findAll();
				/* Proveriti da li vec postoji trazena adresa */
				for (Address a : addresses) {
					if (a.getBrojUUlici().equals(streetNo) && a.getStreet().getId().equals(streetId)) {
						if(user != null){
							user.setName(userName);
							user.setSurname(userSurname);
							User persisted = userDao.merge(user);
							if(persisted == null){
								resp.setContentType("application/json; charset=utf-8");
								PrintWriter out = resp.getWriter();
								resultMapper.writeValue(out, "Nismo uspeli da vam promenimo podatke. Molimo pokusajte ponovo.");
								return;
							}
							
							a.add(user);
							addressDao.merge(a);
							
	
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "USPEH");
							return;
						}else{
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "Doslo je do greske na sesiji. Molimo prijavite se ponovo.");
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
					resultMapper.writeValue(out, "Nismo uspeli da sacuvamo adresu koju ste naveli. Molimo pokusajte ponovo.");
					return;
				}
				
					if(user != null){
						user.setName(userName);
						user.setSurname(userSurname);
						User persisted = userDao.merge(user);
						if(persisted == null){
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "Nismo uspeli da vam izmenimo podatke. MOlimo pokusajte ponoov.");
							return;
						}
						
						adr.add(user);
						addressDao.merge(adr);
	
	
						resp.setContentType("application/json; charset=utf-8");
						PrintWriter out = resp.getWriter();
						resultMapper.writeValue(out, "USPEH");
						return;
					}else{
						resp.setContentType("application/json; charset=utf-8");
						PrintWriter out = resp.getWriter();
						resultMapper.writeValue(out, "Doslo je do greske na sesiji. Molimo prijavite se ponovo.");
						return;
					}
			}catch(Exception ex){
				
				ObjectMapper resultMapper = new ObjectMapper();
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "Greska servera, molimo, pokusajte ponovo.");
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
