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
	
	@Column(name = "ADDRESS_BRUL", nullable = false)
	private Integer brojUUlici;
	
	@ManyToOne
	@JoinColumn(name = "STREET_ID", referencedColumnName = "STREET_ID", nullable = false)
	private Street street;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "ADDRESS") //mappedBy says that owning side is street
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
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "ADDRESS") //mappedBy says that owning side is street
	private Set<RestaurantGuest> guests = new HashSet<RestaurantGuest>();
	
	public void add(RestaurantGuest guest) {
		if (guest.getAddress() != null)
			guest.getAddress().getGuests().remove(guest);
		guest.setAddress(this);
		guests.add(guest);
	}

	public void remove(RestaurantGuest guest) {
		guest.setAddress(null);
		guests.remove(guest);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBrojUUlici() {
		return brojUUlici;
	}

	public void setBrojUUlici(Integer brojUUlici) {
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

	public Set<RestaurantGuest> getGuests() {
		return guests;
	}

	public void setGuests(Set<RestaurantGuest> guests) {
		this.guests = guests;
	}

	public Address(Integer id, Integer brojUUlici, Street street, Set<Restaurant> restaurants,
			Set<RestaurantGuest> guests) {
		super();
		this.id = id;
		this.brojUUlici = brojUUlici;
		this.street = street;
		this.restaurants = restaurants;
		this.guests = guests;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", brojUUlici=" + brojUUlici + "]";
	}
	
	
}
