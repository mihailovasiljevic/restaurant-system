package restaurant.server.session;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.mysql.jdbc.log.Log;

import restaurant.externals.HashPassword;
import restaurant.server.entity.Address;
import restaurant.server.entity.City;
import restaurant.server.entity.Country;
import restaurant.server.entity.Dish;
import restaurant.server.entity.Friend;
import restaurant.server.entity.Invitation;
import restaurant.server.entity.Menu;
import restaurant.server.entity.Reservation;
import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.Street;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.entity.UserType;
import restaurant.server.entity.Visit;

@Stateless
@Remote(Init.class)
public class InitBean implements Init {

	@PersistenceContext(unitName = "restaurant")
	EntityManager em;
	
	@EJB
	private UserDaoLocal userDao;
	
	@EJB
	private UserTypeDaoLocal typeDao;
	
	public void init() {
		List<User> users = userDao.findAll();
		List<UserType> types = typeDao.findAll();
		
		UserType type = null;
		for(UserType t : types){
			if(t.getName().equals("GUEST"))
				type = t;
		}
		
		for(User u : users){
			if(u.getUserType().getName().equals("SYSTEM_MENAGER")){
				
				byte[] salt;
				byte[] hashedPassword;
				

		        User guest1 = new User();
		        guest1.setName("Stefan");
		        guest1.setSurname("Stefanovic");
		        guest1.setEmail("stefan_stephen@gmail.com");
		        guest1.setActivated(true);
		        guest1.setUserType(type);
		        
		        salt = new byte[16];
		        hashedPassword = new byte[256];
		        salt = HashPassword.getNextSalt();
		        char[] passGue1 = {'s','t','e','f','a', 'n'};
		        hashedPassword = HashPassword.hashPassword(passGue1, salt);
		        
		        guest1.setPassword(hashedPassword);
		        guest1.setSalt(salt);
		        
		        em.persist(guest1);
		        
		        User guest2 = new User();
		        guest2.setName("Mirko");
		        guest2.setSurname("Maric");
		        guest2.setEmail("mihailo93@gmail.com");
		        guest2.setActivated(true);
		        guest2.setUserType(type);
		        
		        salt = new byte[16];
		        hashedPassword = new byte[256];
		        salt = HashPassword.getNextSalt();
		        char[] passGue2 = {'m','i','r','k','o'};
		        hashedPassword = HashPassword.hashPassword(passGue2, salt);
		        
		        guest2.setPassword(hashedPassword);
		        guest2.setSalt(salt);
		        
		        em.persist(guest2);
		        
		        User guest3 = new User();
		        guest3.setName("Sanja");
		        guest3.setSurname("Popovic");
		        guest3.setEmail("mihailo.vasiljevic93@gmail.com");
		        guest3.setActivated(true);
		        guest3.setUserType(type);
		        
		        salt = new byte[16];
		        hashedPassword = new byte[256];
		        salt = HashPassword.getNextSalt();
		        char[] passGue3 = {'s','a','n','j','a'};
		        hashedPassword = HashPassword.hashPassword(passGue3, salt);
		        
		        guest3.setPassword(hashedPassword);
		        guest3.setSalt(salt);
		        
		        em.persist(guest3);
		        
		        type.add(guest1);
		        type.add(guest2);
		        type.add(guest3);
		        em.merge(type);
		        
		        guest1.addFriend(guest2);
		        guest2.addFriend(guest1);
		        em.merge(guest1);
		        em.merge(guest2);
				
				
				
			}
		}

	}

}
