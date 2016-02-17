package restaurant.server.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USER")
@NamedQueries({
	@NamedQuery(name = "findUserByEmail", query = "SELECT k FROM User k WHERE k.email like :userEmail"),
	@NamedQuery(name = "findRestaurantMenagerBySystemMenagerId", query = "SELECT k FROM User k WHERE k.systemMenager.id like :userId")
}) 


public class User implements Serializable{

	private static final long serialVersionUID = 4993219445500647218L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Integer id;
	
	@Column(name = "USER_NAME", nullable = false, length=64)
	private String name;

	@Column(name = "USER_SURNAME", nullable = false, length = 64)
	private String surname;

    @Column(name = "USER_EMAIL", nullable = false, length = 80)
    private String email;

    @Column(name = "USER_PASS", nullable = false, length = 260)
    private byte[] password;


    @Column(name = "USER_SALT", nullable = false, length = 260)
    private byte[] salt;
   
    @Column(name = "USER_SESSION_ACTIVE", nullable = false)
    private Boolean isSessionActive = false;
    
    
    @Column(name = "USER_ACCOUNT_TYPE", nullable = false)
    private String accountType = "LOCAL";
    
    @Column(name = "USER_SESSION_ID", nullable = false)
    private String sessionId = "";
    
    @Column(name = "USER_ACTIVATION_TOKEN")
    private byte[] token;

	@ManyToOne
	@JoinColumn(name = "USER_TYPE_ID", referencedColumnName = "USER_TYPE_ID", nullable = false)
	private UserType userType;
	
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
	private Address address;
	

	
	@OneToOne
	@JoinColumn(name = "IMAGE_ID", referencedColumnName = "IMAGE_ID")
	private Image image;

	/*GUEST FIELDS*/
	@Column(name = "USER_ACT", nullable = false)
	private Boolean activated;
	
	@ManyToMany
	@JoinTable(name="FRIEND",
				joinColumns=@JoinColumn(name = "USER_OWNER_ID", referencedColumnName = "USER_ID"),
				inverseJoinColumns=@JoinColumn(name = "USER_FRIEND_ID", referencedColumnName = "USER_ID")
	           )
	private Set<User> myFriends = new HashSet<User>();
	
	public void addFriend(User friend) {
		if (friend != null)
			friend.getMyFriends().remove(friend);
		myFriends.add(friend);
	}

	public void removeFriend(User friend) {
		myFriends.remove(friend);
	}
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "userGuestInvitationSender") //mappedBy says that owning side is street
	private Set<Invitation> sentInvitations = new HashSet<Invitation>();
	
	public void add(Invitation invitation) {
		if (invitation.getUserGuestInvitationSender() != null)
			invitation.getUserGuestInvitationSender().getSentInvitations().remove(invitation);
		invitation.setUserGuestInvitationSender(this);
		sentInvitations.add(invitation);
	}

	public void remove(Invitation invitation) {
		invitation.setUserGuestInvitationSender(null);
		sentInvitations.remove(invitation);
	}
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "userGuestInvitationReceived") //mappedBy says that owning side is street
	private Set<Invitation> receivedInvitations = new HashSet<Invitation>();
	
	public void addInv(Invitation invitation) {
		if (invitation.getUserGuestInvitationReceived() != null)
			invitation.getUserGuestInvitationReceived().getReceivedInvitations().remove(invitation);
		invitation.setUserGuestInvitationReceived(this);
		receivedInvitations.add(invitation);
	}

	public void removeInv(Invitation invitation) {
		invitation.setUserGuestInvitationReceived(null);
		receivedInvitations.remove(invitation);
	}
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "userGuestReservationMaker") //mappedBy says that owning side is street
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	public void add(Reservation reservation) {
		if (reservation.getUserGuestReservationMaker() != null)
			reservation.getUserGuestReservationMaker().getReservations().remove(reservation);
		reservation.setUserGuestReservationMaker(this);
		reservations.add(reservation);
	}

	public void remove(Reservation reservation) {
		reservation.setUserGuestReservationMaker(null);
		reservations.remove(reservation);
	}

	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "user") //mappedBy says that owning side is street
	private Set<Visit> visits = new HashSet<Visit>();
	
	public void add(Visit visit) {
		if (visit.getUser() != null)
			visit.getUser().getVisits().remove(visit);
		visit.setUser(this);
		visits.add(visit);
	}

	public void remove(Visit visit) {
		visit.setUser(null);
		visits.remove(visit);
	}
	/*END GUEST FIELDS*/

	
	/* SYSTEM MENAGER FIELDS*/
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "userSystemMenager") //mappedBy says that owning side is street
	private Set<Restaurant> restaurants = new HashSet<Restaurant>();
	
	public void add(Restaurant restaurant) {
		if (restaurant.getUserSystemMenager() != null)
			restaurant.getUserSystemMenager().getRestaurants().remove(restaurant);
		restaurant.setUserSystemMenager(this);
		restaurants.add(restaurant);
	}

	public void remove(Restaurant restaurant) {
		restaurant.setUserSystemMenager(null);
		restaurants.remove(restaurant);
	}
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "userSystemMenager") //mappedBy says that owning side is street
	private Set<RestaurantType> restaurantTypes = new HashSet<RestaurantType>();
	
	public void add(RestaurantType restaurantType) {
		if (restaurantType.getUserSystemMenager() != null)
			restaurantType.getUserSystemMenager().getRestaurantTypes().remove(restaurantType);
		restaurantType.setUserSystemMenager(this);
		restaurantTypes.add(restaurantType);
	}

	public void remove(RestaurantType restaurantType) {
		restaurantType.setUserSystemMenager(null);
		restaurantTypes.remove(restaurantType);
	}
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "systemMenager") //mappedBy says that owning side is street
	private Set<User> restaurantMenagers = new HashSet<User>();
	
	public void add(User restaurantMenager) {
		if (restaurantMenager.getSystemMenager() != null)
			restaurantMenager.getSystemMenager().getRestaurantMenagers().remove(restaurantMenager);
		restaurantMenager.setSystemMenager(this);
		restaurantMenagers.add(restaurantMenager);
	}
	
	public void remove(User restaurantMenager) {
		restaurantMenager.setSystemMenager(null);
		restaurantMenagers.remove(restaurantMenager);
	}

	/*END OF SYSTEM MENAGER FIELDS*/

	/* RESTAURANT MENAGER FIELDS */
	@ManyToOne
	@JoinColumn(name = "USER_SYS_MEN_ID", referencedColumnName = "USER_ID")
	private User systemMenager;
	
	@ManyToOne
	@JoinColumn(name = "USER_REST_MEN_REST", referencedColumnName = "REST_ID")
	private Restaurant restaurantMenagedBy;
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "userRestaurantMenager") //mappedBy says that owning side is street
	private Set<TablesConfiguration> tablesConfigurations = new HashSet<TablesConfiguration>();
	
	public void add(TablesConfiguration tablesConfiguration) {
		if (tablesConfiguration.getUserRestaurantMenager() != null)
			tablesConfiguration.getUserRestaurantMenager().getTablesConfigurations().remove(tablesConfiguration);
		tablesConfiguration.setUserRestaurantMenager(this);
		tablesConfigurations.add(tablesConfiguration);
	}

	public void remove(TablesConfiguration tablesConfiguration) {
		tablesConfiguration.setUserRestaurantMenager(null);
		tablesConfigurations.remove(tablesConfiguration);
	}
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "userRestaurantMenager") //mappedBy says that owning side is street
	private Set<Menu> menus = new HashSet<Menu>();
	
	public void add(Menu menu) {
		if (menu.getUserRestaurantMenager() != null)
			menu.getUserRestaurantMenager().getMenus().remove(menu);
		menu.setUserRestaurantMenager(this);
		menus.add(menu);
	}

	public void remove(Menu menu) {
		menu.setUserRestaurantMenager(null);
		menus.remove(menu);
	}
	/* END OF RESTAURANT MENAGER FIELDS */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Set<User> getMyFriends() {
		return myFriends;
	}

	public void setMyFriends(Set<User> myFriends) {
		this.myFriends = myFriends;
	}

	public Set<Invitation> getSentInvitations() {
		return sentInvitations;
	}

	public void setSentInvitations(Set<Invitation> sentInvitations) {
		this.sentInvitations = sentInvitations;
	}

	public Set<Invitation> getReceivedInvitations() {
		return receivedInvitations;
	}

	public void setReceivedInvitations(Set<Invitation> receivedInvitations) {
		this.receivedInvitations = receivedInvitations;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Set<RestaurantType> getRestaurantTypes() {
		return restaurantTypes;
	}

	public void setRestaurantTypes(Set<RestaurantType> restaurantTypes) {
		this.restaurantTypes = restaurantTypes;
	}

	public Set<User> getRestaurantMenagers() {
		return restaurantMenagers;
	}

	public void setRestaurantMenagers(Set<User> restaurantMenagers) {
		this.restaurantMenagers = restaurantMenagers;
	}

	public User getSystemMenager() {
		return systemMenager;
	}

	public void setSystemMenager(User systemMenager) {
		this.systemMenager = systemMenager;
	}

	public Set<TablesConfiguration> getTablesConfigurations() {
		return tablesConfigurations;
	}

	public void setTablesConfigurations(Set<TablesConfiguration> tablesConfigurations) {
		this.tablesConfigurations = tablesConfigurations;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	
	
	public Set<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}
	

	public Boolean getIsSessionActive() {
		return isSessionActive;
	}

	public void setIsSessionActive(Boolean isSessionActive) {
		this.isSessionActive = isSessionActive;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public Restaurant getRestaurantMenagedBy() {
		return restaurantMenagedBy;
	}

	public void setRestaurantMenagedBy(Restaurant restaurantMenagedBy) {
		this.restaurantMenagedBy = restaurantMenagedBy;
	}


	public User(String name, String surname, String email, byte[] password, byte[] salt, Boolean isSessionActive,
			String accountType, String sessionId, byte[] token, UserType userType, Address address, Image image,
			Boolean activated, Set<User> myFriends, Set<Invitation> sentInvitations,
			Set<Invitation> receivedInvitations, Set<Reservation> reservations, Set<Visit> visits,
			Set<Restaurant> restaurants, Set<RestaurantType> restaurantTypes, Set<User> restaurantMenagers,
			User systemMenager, Restaurant restaurantMenagedBy, Set<TablesConfiguration> tablesConfigurations,
			Set<Menu> menus) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.salt = salt;
		this.isSessionActive = isSessionActive;
		this.accountType = accountType;
		this.sessionId = sessionId;
		this.token = token;
		this.userType = userType;
		this.address = address;
		this.image = image;
		this.activated = activated;
		this.myFriends = myFriends;
		this.sentInvitations = sentInvitations;
		this.receivedInvitations = receivedInvitations;
		this.reservations = reservations;
		this.visits = visits;
		this.restaurants = restaurants;
		this.restaurantTypes = restaurantTypes;
		this.restaurantMenagers = restaurantMenagers;
		this.systemMenager = systemMenager;
		this.restaurantMenagedBy = restaurantMenagedBy;
		this.tablesConfigurations = tablesConfigurations;
		this.menus = menus;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}
	
	
}
