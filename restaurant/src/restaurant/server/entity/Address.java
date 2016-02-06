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
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable{

	private static final long serialVersionUID = -4694497404092498742L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ADDRESS_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "ADDRESS_BRUL", nullable = false, length=5)
	private String brojUUlici;
	
	@ManyToOne
	@JoinColumn(name = "STREET_ID", referencedColumnName = "STREET_ID")
	private Street street;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "address") //mappedBy says that owning side is street
	private Set<Restaurant> restaurants = new HashSet<Restaurant>();
	
	public void add(Restaurant restaurant) {
		if (restaurant.getAddress() != null)
			restaurant.getAddress().getRestaurants().remove(restaurant);
		restaurant.setAddress(this);
		restaurants.add(restaurant);
	}

	public void remove(Restaurant restaurant) {
		restaurant.setAddress(null);
		restaurants.remove(restaurant);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "address") //mappedBy says that owning side is street
	private Set<User> users = new HashSet<User>();
	
	public void add(User guest) {
		if (guest.getAddress() != null)
			guest.getAddress().getUsers().remove(guest);
		guest.setAddress(this);
		users.add(guest);
	}

	public void remove(User guest) {
		guest.setAddress(null);
		users.remove(guest);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrojUUlici() {
		return brojUUlici;
	}

	public void setBrojUUlici(String brojUUlici) {
		this.brojUUlici = brojUUlici;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Address(String brojUUlici, Street street, Set<Restaurant> restaurants, Set<User> users) {
		super();
		this.brojUUlici = brojUUlici;
		this.street = street;
		this.restaurants = restaurants;
		this.users = users;
	}

	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", brojUUlici=" + brojUUlici + "]";
	}
}
