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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANT")
@NamedQuery(name = "findRestaurantByUserId", query = "SELECT k FROM Restaurant k WHERE k.userSystemMenager.id like :userId")
public class Restaurant implements Serializable{
		
	private static final long serialVersionUID = -2100264736088386350L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_NAME", nullable = false, length = 128)
	private String name;
	
	@Column(name = "REST_GRADE")
	private Integer grade;
	
	@ManyToOne
	@JoinColumn(name = "REST_TYPE_ID", referencedColumnName = "REST_TYPE_ID")
	private RestaurantType restaurantType;
	
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
	private Address address;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private User userSystemMenager;
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "restaurant") //mappedBy says that owning side is street
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
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "restaurant") //mappedBy says that owning side is street
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
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "restaurant") //mappedBy says that owning side is street
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

	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "restaurant") //mappedBy says that owning side is street
	private Set<Visit> visits = new HashSet<Visit>();
	
	public void add(Visit visit) {
		if (visit.getRestaurant() != null)
			visit.getRestaurant().getVisits().remove(visit);
		visit.setRestaurant(this);
		visits.add(visit);
	}

	public void remove(Visit visit) {
		visit.setRestaurant(null);
		visits.remove(visit);
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

	public User getUserSystemMenager() {
		return userSystemMenager;
	}

	public void setUserSystemMenager(User userSystemMenager) {
		this.userSystemMenager = userSystemMenager;
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

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	
	public Set<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}

	public Restaurant() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Restaurant(String name, Integer grade, RestaurantType restaurantType, Address address,
			User userSystemMenager, Set<Menu> menus, Set<TablesConfiguration> tablesConfigurations,
			Set<User> users, Set<Reservation> reservations, Set<Visit> visits) {
		super();
		this.name = name;
		this.grade = grade;
		this.restaurantType = restaurantType;
		this.address = address;
		this.userSystemMenager = userSystemMenager;
		this.menus = menus;
		this.tablesConfigurations = tablesConfigurations;
		this.reservations = reservations;
		this.visits = visits;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", grade=" + grade + "]";
	}

}
