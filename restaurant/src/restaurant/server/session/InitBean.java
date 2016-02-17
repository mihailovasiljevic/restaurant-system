package restaurant.server.session;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
	
	public void init() {
		
		/**
		 * Create types of user.
		 * ------------------------------------------------------
		 */
		
		UserType guest = new UserType();
		guest.setName("GUEST");
		em.persist(guest);
		
		UserType systemMenager = new UserType();
		systemMenager.setName("SYSTEM_MENAGER");
		em.persist(systemMenager);
		
		UserType restaurantMenager = new UserType();
		restaurantMenager.setName("RESTAURANT_MENAGER");
		em.persist(restaurantMenager);
		
		/**
		 * ------------------------------------------------------
		 */
		
		/**
		 * Create system menagers.
		 * ------------------------------------------------------
		 */
		
		//prepare password hashing
        byte[] salt;
        byte[] hashedPassword;
        
        User systemMenager1 = new User();
        systemMenager1.setName("Marko");
        systemMenager1.setSurname("Jovanovic");
        systemMenager1.setEmail("marko_jovanovic@gmail.com");
        systemMenager1.setActivated(true);
        systemMenager1.setUserType(systemMenager);
        
        salt = new byte[16];
        hashedPassword = new byte[256];
        salt = HashPassword.getNextSalt();
        systemMenager1.setSalt(salt);
        char[] pass = {'m','a','r','k','o'};
        hashedPassword = HashPassword.hashPassword(pass, systemMenager1.getSalt());
        systemMenager1.setPassword(hashedPassword);

        
        
        em.persist(systemMenager1);
        
        User systemMenager2 = new User();
        systemMenager2.setName("Mihailo");
        systemMenager2.setSurname("Vasiljevic");
        systemMenager2.setEmail("mihailo931@gmail.com");
        systemMenager2.setActivated(true);
        systemMenager2.setUserType(systemMenager);
        
        salt = new byte[16];
        hashedPassword = new byte[256];
        salt = HashPassword.getNextSalt();
        char[] pass2 = {'m','i','h','a','i','l','o'};
        hashedPassword = HashPassword.hashPassword(pass2, salt);
        
        systemMenager2.setPassword(hashedPassword);
        systemMenager2.setSalt(salt);
        
        em.persist(systemMenager2);
        
        systemMenager.add(systemMenager1);
        systemMenager.add(systemMenager2);
        em.merge(systemMenager);
	}

}
