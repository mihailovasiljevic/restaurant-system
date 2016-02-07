package restaurant.server.session;

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
        systemMenager1.setNumberOfVisits(0);
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
        systemMenager2.setEmail("mihailo93@gmail.com");
        systemMenager2.setActivated(true);
        systemMenager2.setNumberOfVisits(0);
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
		
		/**systemMenager1
		 * ------------------------------------------------------
		 */
        
		/**
		 * Create restaurant menagers.
		 * ------------------------------------------------------
		 */

        User restaurantMenager1 = new User();
        restaurantMenager1.setName("Nikola");
        restaurantMenager1.setSurname("Stefanovic");
        restaurantMenager1.setEmail("nik_stefan@gmail.com");
        restaurantMenager1.setActivated(true);
        restaurantMenager1.setNumberOfVisits(0);
        restaurantMenager1.setUserType(restaurantMenager);
        
        salt = new byte[16];
        hashedPassword = new byte[256];
        salt = HashPassword.getNextSalt();
        char[] passNik = {'n','i','k','o','l','a'};
        hashedPassword = HashPassword.hashPassword(passNik, salt);
        
        restaurantMenager1.setPassword(hashedPassword);
        restaurantMenager1.setSalt(salt);
        restaurantMenager1.setSystemMenager(systemMenager2);
        
        em.persist(restaurantMenager1);
        
        systemMenager1.add(restaurantMenager1);
        em.merge(systemMenager2);
        
        User restaurantMenager2 = new User();
        restaurantMenager2.setName("Janko");
        restaurantMenager2.setSurname("Jankovic");
        restaurantMenager2.setEmail("jan_jan@gmail.com");
        restaurantMenager2.setActivated(true);
        restaurantMenager2.setNumberOfVisits(0);
        restaurantMenager2.setUserType(restaurantMenager);
        
        salt = new byte[16];
        hashedPassword = new byte[256];
        salt = HashPassword.getNextSalt();
        char[] passJan = {'j','a','n','k','o'};
        hashedPassword = HashPassword.hashPassword(passJan, salt);
        
        restaurantMenager2.setPassword(hashedPassword);
        restaurantMenager2.setSalt(salt);
        restaurantMenager2.setSystemMenager(systemMenager2);
        
        em.persist(restaurantMenager2);
        
        systemMenager1.add(restaurantMenager2);
        em.merge(systemMenager2);
        
        restaurantMenager.add(restaurantMenager1);
        restaurantMenager.add(restaurantMenager2);
        em.merge(restaurantMenager);
        
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create guests.
		 * ------------------------------------------------------
		 */
        
        User guest1 = new User();
        guest1.setName("Stefan");
        guest1.setSurname("Stefanovic");
        guest1.setEmail("stefan_stephen@gmail.com");
        guest1.setActivated(true);
        guest1.setNumberOfVisits(0);
        guest1.setUserType(systemMenager);
        
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
        guest2.setNumberOfVisits(0);
        guest2.setUserType(systemMenager);
        
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
        guest3.setNumberOfVisits(0);
        guest3.setUserType(systemMenager);
        
        salt = new byte[16];
        hashedPassword = new byte[256];
        salt = HashPassword.getNextSalt();
        char[] passGue3 = {'s','a','n','j','a'};
        hashedPassword = HashPassword.hashPassword(passGue3, salt);
        
        guest3.setPassword(hashedPassword);
        guest3.setSalt(salt);
        
        em.persist(guest3);
        
        guest.add(guest1);
        guest.add(guest2);
        guest.add(guest3);
        em.merge(guest);
        
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create country.
		 * ------------------------------------------------------
		 */
        Country serbia = new Country();
        serbia.setName("Serbia");
        em.persist(serbia);
        
        Country england = new Country();
        england.setName("Egnland");
        em.persist(england);
        
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create city.
		 * ------------------------------------------------------
		 */
        City noviSad = new City();
        noviSad.setName("Novi Sad");
        noviSad.setCountry(serbia);
        em.persist(noviSad);
        
        City belgrade = new City();
        belgrade.setName("Beograd");
        belgrade.setCountry(serbia);
        em.persist(belgrade);
        
        serbia.add(noviSad);
        serbia.add(belgrade);
        
        em.merge(serbia);
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create street.
		 * ------------------------------------------------------
		 */
        Street nineJugs = new Street();
        nineJugs.setName("Devet Jugovica");
        nineJugs.setCity(noviSad);
        em.persist(nineJugs);
        
        Street princeMarko = new Street();
        princeMarko.setName("Kraljevica Marka");
        princeMarko.setCity(noviSad);
        em.persist(princeMarko);       
        
        Street kosovo = new Street();
        kosovo.setName("Kosovska");
        kosovo.setCity(noviSad);
        em.persist(kosovo);   
        
        Street jjzmaj = new Street();
        jjzmaj.setName("Jovana Jovanovica Zmaja");
        jjzmaj.setCity(noviSad);
        em.persist(jjzmaj);  
        
        Street green = new Street();
        green.setName("Zelena");
        green.setCity(noviSad);
        em.persist(green);   
        
        Street bulOsl = new Street();
        bulOsl.setName("Bulevar oslobodjenja");
        bulOsl.setCity(noviSad);
        em.persist(bulOsl); 
        
        Street jna = new Street();
        jna.setName("Jugoslovenske narodne armije");
        jna.setCity(belgrade);
        em.persist(jna);  
        
        Street tito = new Street();
        tito.setName("Marsala Tita");
        tito.setCity(belgrade);
        em.persist(tito);  
        
        Street urosPredic = new Street();
        urosPredic.setName("Urosa Predica");
        urosPredic.setCity(belgrade);
        em.persist(urosPredic);  
        
        Street ppnj = new Street();
        ppnj.setName("Petra I Petrovica Njegosa");
        ppnj.setCity(belgrade);
        em.persist(ppnj);  
        
        Street bulKingAlex = new Street();
        bulKingAlex.setName("Bulevar kralja Aleksandra");
        bulKingAlex.setCity(belgrade);
        em.persist(bulKingAlex);  
        

        
        
        noviSad.add(nineJugs);
        noviSad.add(princeMarko);
        noviSad.add(kosovo);
        noviSad.add(jjzmaj);
        noviSad.add(bulOsl);
        noviSad.add(green);
        
        em.merge(noviSad);
        
        belgrade.add(jna);
        belgrade.add(tito);
        belgrade.add(urosPredic);
        belgrade.add(ppnj);
        belgrade.add(bulKingAlex);
        em.merge(belgrade);
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create address.
		 * ------------------------------------------------------
		 */
        
        Address sys1 = new Address();
        sys1.setBrojUUlici("120");
        sys1.setStreet(nineJugs);
        em.persist(sys1);
        
        Address sys2 = new Address();
        sys2.setBrojUUlici("BB");
        sys2.setStreet(jna);
        em.persist(sys2);
        
        Address men1 = new Address();
        men1.setBrojUUlici("16C");
        men1.setStreet(tito);
        em.persist(men1);
        
        Address men2 = new Address();
        men2.setBrojUUlici("1A");
        men2.setStreet(bulOsl);
        em.persist(men2);
        
        Address gue1 = new Address();
        gue1.setBrojUUlici("32");
        gue1.setStreet(nineJugs);
        em.persist(gue1);
        
        Address gue2 = new Address();
        gue2.setBrojUUlici("48");
        gue2.setStreet(kosovo);
        em.persist(gue2);
        
        Address gue3 = new Address();
        gue3.setBrojUUlici("96");
        gue3.setStreet(nineJugs);
        em.persist(gue3);
        
        Address rest1 = new Address();
        rest1.setBrojUUlici("101");
        rest1.setStreet(ppnj);
        em.persist(rest1);
        
        Address rest2 = new Address();
        rest2.setBrojUUlici("65C");
        rest2.setStreet(bulKingAlex);
        em.persist(rest2);
        
        nineJugs.add(sys1);
        nineJugs.add(gue1);
        nineJugs.add(gue3);
        jna.add(sys2);
        tito.add(men1);
        bulOsl.add(men2);
        kosovo.add(gue2);
        ppnj.add(rest1);
        bulKingAlex.add(rest2);
        
        em.merge(nineJugs);
        em.merge(jna);
        em.merge(tito);
        em.merge(bulOsl);
        em.merge(kosovo);
        em.merge(ppnj);
        em.merge(bulKingAlex);
        
        
        systemMenager1.setAddress(sys1);
        systemMenager2.setAddress(sys2);
        restaurantMenager1.setAddress(men1);
        restaurantMenager2.setAddress(men2);
        guest1.setAddress(gue1);
        guest2.setAddress(gue2);
        guest3.setAddress(gue3);
        
        em.merge(systemMenager1);
        em.merge(systemMenager2);
        em.merge(restaurantMenager1);
        em.merge(restaurantMenager2);
        em.merge(guest1);
        em.merge(guest2);
        em.merge(guest3);
        
        sys1.add(systemMenager1);
        sys2.add(systemMenager2);
        men1.add(restaurantMenager1);
        men2.add(restaurantMenager2);
        gue1.add(guest1);
        gue2.add(guest2);
        gue3.add(guest3);
        
        em.merge(sys1);
        em.merge(sys2);
        em.merge(men1);
        em.merge(men2);
        em.merge(gue1);
        em.merge(gue2);
        em.merge(gue3);
        
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create images.
		 * ------------------------------------------------------
		 */
        
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create restaurant types.
		 * ------------------------------------------------------
		 */
        RestaurantType chinese = new RestaurantType();
        chinese.setName("KINESKI");
        chinese.setUserSystemMenager(systemMenager1);
        em.persist(chinese);
        
        RestaurantType vegan = new RestaurantType();
        vegan.setName("VEGANSKI");
        vegan.setUserSystemMenager(systemMenager2);
        em.persist(vegan);
        
        RestaurantType domestic = new RestaurantType();
        domestic.setName("DOMACA KUHINJA");
        domestic.setUserSystemMenager(systemMenager2);
        em.persist(domestic);
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create restaurants.
		 * ------------------------------------------------------
		 */
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName("RESTORAN KARADJORDJE");
        restaurant1.setRestaurantType(domestic);
        restaurant1.setUserSystemMenager(systemMenager2);
        restaurant1.setAddress(rest1);
        restaurant1.add(guest1);
        restaurant1.add(guest2);
        restaurant1.add(guest3);
        
        em.persist(restaurant1);
        
        
        rest1.add(restaurant1);
        em.merge(rest1);
        
        
        guest1.setRestaurant(restaurant1);
        guest2.setRestaurant(restaurant1);
        guest3.setRestaurant(restaurant1);
        em.merge(guest1);
        em.merge(guest2);
        em.merge(guest3);
        
        
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("GOGO RESTORAN");
        restaurant2.setRestaurantType(chinese);
        restaurant2.setUserSystemMenager(systemMenager2);
        restaurant2.setAddress(rest2);
        
        em.persist(restaurant2);
        
        rest2.add(restaurant2);
        em.merge(rest1);
        
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create friends.
		 * ------------------------------------------------------
		 */
        guest1.addFriend(guest2);
        System.out.println("VLASNIK: " + guest1.getName());
        System.out.println("PRIJATELJ: " + guest2.getName());
        guest2.addFriend(guest1);
        System.out.println("VLASNIK: " + guest2.getName());
        System.out.println("PRIJATELJ: " + guest1.getName());
        guest1.addFriend(guest3);
        System.out.println("VLASNIK: " + guest1.getName());
        System.out.println("PRIJATELJ: " + guest3.getName());
        guest3.addFriend(guest1);
        System.out.println("VLASNIK: " + guest3.getName());
        System.out.println("PRIJATELJ: " + guest1.getName());
        
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create menus.
		 * ------------------------------------------------------
		 */
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	String dateInString = "31-08-2015 10:20:56";
    	Menu menuRes1 = new Menu();
    	Date date;
		try {
			date = sdf.parse(dateInString);
	        menuRes1.setName("Jelovnik");
	        menuRes1.setRestaurant(restaurant1);
	        menuRes1.setUserRestaurantMenager(restaurantMenager1);
	        menuRes1.setDateFrom(date);
	        em.persist(menuRes1);
	        
	        restaurant1.add(menuRes1);
	        restaurantMenager1.add(menuRes1);
	        em.merge(restaurant1);
	        em.merge(restaurantMenager1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	dateInString = "22-10-2015 12:00:56";
    	Menu menuRes2 = new Menu();
		try {
			date = sdf.parse(dateInString);
	        menuRes2.setName("Jelovnik");
	        menuRes2.setRestaurant(restaurant2);
	        menuRes2.setUserRestaurantMenager(restaurantMenager2);
	        menuRes2.setDateFrom(date);
	        em.persist(menuRes2);
	        
	        restaurant2.add(menuRes2);
	        restaurantMenager2.add(menuRes2);
	        em.merge(restaurant2);
	        em.merge(restaurantMenager2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}


		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create dishes.
		 * ------------------------------------------------------
		 */
        Dish snitzl = new Dish();
        snitzl.setDescription("Karadjordjeva snicla.");
        snitzl.setName("Karadjordjeva snicla");
        snitzl.setPrice(150.00);
        snitzl.setMenu(menuRes1);
        em.persist(snitzl);
        
        menuRes1.add(snitzl);
        em.merge(menuRes1);
        
        Dish kebab = new Dish();
        kebab.setDescription("Cevap.");
        kebab.setName("Cevap");
        kebab.setPrice(350.00);
        kebab.setMenu(menuRes1);
        em.persist(kebab);
        
        menuRes1.add(kebab);
        em.merge(menuRes1);
        
		/**
		 * ------------------------------------------------------
		 */ 
        

        
		/**
		 * Create tables configurations.
		 * ------------------------------------------------------
		 */
        TablesConfiguration conf1 = new TablesConfiguration();
        conf1.setCurrent(true);
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	dateInString = "02-01-2016 12:00:00";
		try {
			date = sdf.parse(dateInString);
	        conf1.setDateFrom(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		conf1.setName("Januarska konfiguracija stolova.");
		conf1.setNumberOfCols(3);
		conf1.setNumberOfRows(3);
		conf1.setRestaurant(restaurant1);
		conf1.setUserRestaurantMenager(restaurantMenager1);
		em.persist(conf1);
		restaurant1.add(conf1);
		restaurantMenager1.add(conf1);
		em.merge(restaurant1);
		em.merge(restaurantMenager1);

		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create tables.
		 * ------------------------------------------------------
		 */
        RestaurantTable table00 = new RestaurantTable();
        table00.setRow(0);
        table00.setCol(0);
        table00.setName("00");
        table00.setReserved(false);
        table00.setTablesConfiguration(conf1);
        em.persist(table00);
        
        RestaurantTable table01 = new RestaurantTable();
        table01.setRow(0);
        table01.setCol(1);
        table01.setName("01");
        table01.setReserved(false);
        table01.setTablesConfiguration(conf1);
        em.persist(table01);
        
        RestaurantTable table02 = new RestaurantTable();
        table02.setRow(0);
        table02.setCol(2);
        table02.setName("02");
        table02.setReserved(false);
        table02.setTablesConfiguration(conf1);
        em.persist(table02);
        
        RestaurantTable table10 = new RestaurantTable();
        table10.setRow(1);
        table10.setCol(0);
        table10.setName("10");
        table10.setReserved(false);
        table10.setTablesConfiguration(conf1);
        em.persist(table10);
        
        RestaurantTable table20 = new RestaurantTable();
        table20.setRow(2);
        table20.setCol(0);
        table20.setName("20");
        table20.setReserved(false);
        table20.setTablesConfiguration(conf1);
        em.persist(table20);
        
        RestaurantTable table11 = new RestaurantTable();
        table11.setRow(1);
        table11.setCol(1);
        table11.setName("11");
        table11.setReserved(false);
        table11.setTablesConfiguration(conf1);
        em.persist(table11);
        
        RestaurantTable table12 = new RestaurantTable();
        table12.setRow(1);
        table12.setCol(2);
        table12.setName("12");
        table12.setReserved(false);
        table12.setTablesConfiguration(conf1);
        em.persist(table12);
        
        RestaurantTable table21 = new RestaurantTable();
        table21.setRow(2);
        table21.setCol(1);
        table21.setName("21");
        table21.setReserved(false);
        table21.setTablesConfiguration(conf1);
        em.persist(table21);
        
        RestaurantTable table22 = new RestaurantTable();
        table22.setRow(2);
        table22.setCol(2);
        table22.setName("22");
        table22.setReserved(false);
        table22.setTablesConfiguration(conf1);
        em.persist(table22);
        
        conf1.add(table00);
        conf1.add(table01);
        conf1.add(table02);
        conf1.add(table10);
        conf1.add(table20);
        conf1.add(table11);
        conf1.add(table12);
        conf1.add(table21);
        conf1.add(table22);
        em.merge(conf1);
		/**
		 * ------------------------------------------------------
		 */ 
        
		/**
		 * Create reservation for guest1 and he calls both his friends.
		 * ------------------------------------------------------
		 */
        Reservation res = new Reservation();
        res.setDate(new Date());
        res.setForHowLong(2);
        res.setGrade(3);
        res.setName("Rezervacija"+res.getId());
        res.setRestaurant(restaurant1);
        res.setRestaurantTable(table00);
        res.setUserGuestReservationMaker(guest1);
        em.persist(res);
        
        restaurant1.add(res);
        table00.setReserved(true);
        table00.add(res);
        table00.setReservedDate(res.getDate());
        table00.setReservedFor(res.getForHowLong());
        guest1.add(res);
        em.merge(restaurant1);
        em.merge(table00);
        em.merge(guest1);
        
        Invitation inv1 = new Invitation();
        inv1.setInvitationAccepted(false);
        inv1.setName("Invitation"+inv1.getId());
        inv1.setReservation(res);
        inv1.setUserGuestInvitationReceived(guest2);
        inv1.setUserGuestInvitationSender(guest1);
        em.persist(inv1);
        
        res.add(inv1);
        guest1.addInv(inv1);
        guest2.addInv(inv1);
        em.merge(res);
        em.merge(guest1);
        em.merge(guest2);
        
        Invitation inv2 = new Invitation();
        inv2.setInvitationAccepted(false);
        inv2.setName("Invitation"+inv1.getId());
        inv2.setReservation(res);
        inv2.setUserGuestInvitationReceived(guest3);
        inv2.setUserGuestInvitationSender(guest1);
        em.persist(inv2);
        
        res.add(inv2);
        guest1.addInv(inv2);
        guest3.addInv(inv2);
        em.merge(res);
        em.merge(guest1);
        em.merge(guest3);
        
        
		/**
		 * ------------------------------------------------------
		 */ 

		/**
		 * RESTORAN 1 TIP: -------------------------------
		 */ 
        		System.out.println("RESTORAN 1 TIP: " + restaurant1.getRestaurantType().getName());
		/**
		 * PRIJATELJI GUEST1----------------------------------------
		 */ 
        		System.out.println("Prijatelji 1: ");
        		Iterator<User> iterator = guest1.getMyFriends().iterator(); 
        		while (iterator.hasNext()){
        			   System.out.println("Prijatelj: "+ ((User)iterator.next()).getName());  
        		}
        		
        		System.out.println("Prijatelji 2: ");
        		iterator = guest2.getMyFriends().iterator(); 
        		while (iterator.hasNext()){
        			   System.out.println("Prijatelj: "+ ((User)iterator.next()).getName());  
        		}
        		
        		System.out.println("Prijatelji 3: ");
        		iterator = guest3.getMyFriends().iterator(); 
        		while (iterator.hasNext()){
        			   System.out.println("Prijatelj: "+ ((User)iterator.next()).getName());  
        		}
	}
}
