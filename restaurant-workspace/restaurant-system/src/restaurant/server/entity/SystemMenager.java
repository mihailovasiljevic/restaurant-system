package restaurant.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SYSTEM_MENAGER")
public class SystemMenager implements Serializable{

	private static final long serialVersionUID = -3387534626139060334L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "SYS_MEN_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "SYS_MEN_NAME", nullable = false, length=64)
	private String name;
	
	@Column(name = "SYS_MEN_SURNAME", nullable = false, length = 64)
	private String surname;
	
	@Column(name = "SYS_MEN_EMAIL", nullable = false, length = 80)
	private String email;
	
	@Column(name = "SYS_MEN_PASS", nullable = false, length = 256)
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID", nullable = false)
	private Address address;
	
	@OneToOne
	@JoinColumn(name = "IMAGE_ID", referencedColumnName = "IMAGE_ID", nullable = true)
	private Image image;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "systemManager") //mappedBy says that owning side is street
	private Set<Restaurant> restaurants = new HashSet<Restaurant>();
	
	public void add(Restaurant restaurant) {
		if (restaurant.getSystemMenager() != null)
			restaurant.getSystemMenager().getRestaurants().remove(restaurant);
		restaurant.setSystemMenager(this);
		restaurants.add(restaurant);
	}

	public void remove(Restaurant restaurant) {
		restaurant.setSystemMenager(null);
		restaurants.remove(restaurant);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "systemManager") //mappedBy says that owning side is street
	private Set<RestaurantType> restaurantTypes = new HashSet<RestaurantType>();
	
	public void add(RestaurantType restaurantType) {
		if (restaurantType.getSystemMenager() != null)
			restaurantType.getSystemMenager().getRestaurantTypes().remove(restaurantType);
		restaurantType.setSystemMenager(this);
		restaurantTypes.add(restaurantType);
	}

	public void remove(RestaurantType restaurantType) {
		restaurantType.setSystemMenager(null);
		restaurantTypes.remove(restaurantType);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "systemManager") //mappedBy says that owning side is street
	private Set<RestaurantMenager> restaurantMenagers = new HashSet<RestaurantMenager>();
	
	public void add(RestaurantMenager restaurantMenager) {
		if (restaurantMenager.getSystemMenager() != null)
			restaurantMenager.getSystemMenager().getRestaurantMenagers().remove(restaurantMenager);
		restaurantMenager.setSystemMenager(this);
		restaurantMenagers.add(restaurantMenager);
	}

	public void remove(RestaurantMenager restaurantMenager) {
		restaurantMenager.setSystemMenager(null);
		restaurantMenagers.remove(restaurantMenager);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public Set<RestaurantMenager> getRestaurantMenagers() {
		return restaurantMenagers;
	}

	public void setRestaurantMenagers(Set<RestaurantMenager> restaurantMenagers) {
		this.restaurantMenagers = restaurantMenagers;
	}

	public SystemMenager(Integer id, String name, String surname, String email, String password, Address address,
			Image image, Set<Restaurant> restaurants, Set<RestaurantType> restaurantTypes,
			Set<RestaurantMenager> restaurantMenagers) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.image = image;
		this.restaurants = restaurants;
		this.restaurantTypes = restaurantTypes;
		this.restaurantMenagers = restaurantMenagers;
	}
	
	
	
	
	
}
