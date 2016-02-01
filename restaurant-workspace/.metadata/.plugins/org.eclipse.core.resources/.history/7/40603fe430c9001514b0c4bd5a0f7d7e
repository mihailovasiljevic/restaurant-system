package restaurant.server.entity;
/**
 * 
 * @author Mihailo
 * This class represents table that is used
 * for restaurant tracking.
 */

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
/**
 *GenerationType.IDENTITY  The database will assign an identity value on insert.
 *assign a unique value to your identity fields automatically
 */
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANT")
public class Restaurant implements Serializable{
		
	private static final long serialVersionUID = -2100264736088386350L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_NAME", nullable = false, length = 128)
	private String name;
	
	@Column(name = "REST_GRADE", nullable = true)
	private Integer grade;
	
	@ManyToOne
	@JoinColumn(name = "REST_TYPE_ID", referencedColumnName = "REST_TYPE_ID")
	private RestaurantType restaurantType;
	
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
	private Address address;
	
	@ManyToOne
	@JoinColumn(name = "SYS_MEN_ID", referencedColumnName = "SYS_MEN_ID")
	private SystemMenager systemMenager;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantManager") //mappedBy says that owning side is street
	private Set<Menu> menus = new HashSet<Menu>();
	
	public void add(Menu menu) {
		if (menu.getRestaurant() != null)
			menu.getRestaurant().getMenus().remove(menu);
		menu.setRestaurant(this);
		menus.add(menu);
	}

	public void remove(Menu menu) {
		menu.setRestaurant(null);
		menus.remove(menu);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantManager") //mappedBy says that owning side is street
	private Set<TablesConfiguration> tablesConfigurations = new HashSet<TablesConfiguration>();
	
	public void add(TablesConfiguration tablesConfiguration) {
		if (tablesConfiguration.getRestaurant() != null)
			tablesConfiguration.getRestaurant().getTablesConfigurations().remove(tablesConfiguration);
		tablesConfiguration.setRestaurant(this);
		tablesConfigurations.add(tablesConfiguration);
	}

	public void remove(TablesConfiguration tablesConfiguration) {
		tablesConfiguration.setRestaurant(null);
		tablesConfigurations.remove(tablesConfiguration);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantManager") //mappedBy says that owning side is street
	private Set<RestaurantGuest> restaurantGuests = new HashSet<RestaurantGuest>();
	
	public void add(RestaurantGuest restaurantGuest) {
		if (restaurantGuest.getRestaurant() != null)
			restaurantGuest.getRestaurant().getRestaurantGuests().remove(restaurantGuest);
		restaurantGuest.setRestaurant(this);
		restaurantGuests.add(restaurantGuest);
	}

	public void remove(RestaurantGuest restaurantGuest) {
		restaurantGuest.setRestaurant(null);
		restaurantGuests.remove(restaurantGuest);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantManager") //mappedBy says that owning side is street
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	public void add(Reservation reservation) {
		if (reservation.getRestaurant() != null)
			reservation.getRestaurant().getReservations().remove(reservation);
		reservation.setRestaurant(this);
		reservations.add(reservation);
	}

	public void remove(Reservation reservation) {
		reservation.setRestaurant(null);
		reservations.remove(reservation);
	}

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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public RestaurantType getRestaurantType() {
		return restaurantType;
	}

	public void setRestaurantType(RestaurantType restaurantType) {
		this.restaurantType = restaurantType;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public SystemMenager getSystemMenager() {
		return systemMenager;
	}

	public void setSystemMenager(SystemMenager systemMenager) {
		this.systemMenager = systemMenager;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<TablesConfiguration> getTablesConfigurations() {
		return tablesConfigurations;
	}

	public void setTablesConfigurations(Set<TablesConfiguration> tablesConfigurations) {
		this.tablesConfigurations = tablesConfigurations;
	}

	public Set<RestaurantGuest> getRestaurantGuests() {
		return restaurantGuests;
	}

	public void setRestaurantGuests(Set<RestaurantGuest> restaurantGuests) {
		this.restaurantGuests = restaurantGuests;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Restaurant(Integer id, String name, Integer grade, RestaurantType restaurantType, Address address,
			SystemMenager systemMenager, Set<Menu> menus, Set<TablesConfiguration> tablesConfigurations,
			Set<RestaurantGuest> restaurantGuests, Set<Reservation> reservations) {
		super();
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.restaurantType = restaurantType;
		this.address = address;
		this.systemMenager = systemMenager;
		this.menus = menus;
		this.tablesConfigurations = tablesConfigurations;
		this.restaurantGuests = restaurantGuests;
		this.reservations = reservations;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", grade=" + grade + "]";
	}
	
}
