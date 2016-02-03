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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TYPE")
public class UserType implements Serializable{

	private static final long serialVersionUID = -6938543304472549723L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_TYPE_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "USER_TYPE_NAME", nullable = false, length=64)
	private String name;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "userType")
	private Set<RestaurantGuest> restaurantGuests = new HashSet<RestaurantGuest>();
	
	public void add(RestaurantGuest rst) {
		if (rst.getUserType() != null)
			rst.getUserType().getRestaurantGuests().remove(rst);
		rst.setUserType(this);
		restaurantGuests.add(rst);
	}

	public void remove(RestaurantGuest rst) {
		rst.setUserType(null);
		restaurantGuests.remove(rst);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "userType")
	private Set<RestaurantMenager> restaurantMenagers = new HashSet<RestaurantMenager>();
	
	public void add(RestaurantMenager rst) {
		if (rst.getUserType() != null)
			rst.getUserType().getRestaurantMenagers().remove(rst);
		rst.setUserType(this);
		restaurantMenagers.add(rst);
	}

	public void remove(RestaurantMenager rst) {
		rst.setUserType(null);
		restaurantMenagers.remove(rst);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "userType")
	private Set<SystemMenager> systemMenagers = new HashSet<SystemMenager>();
	
	public void add(SystemMenager rst) {
		if (rst.getUserType() != null)
			rst.getUserType().getSystemMenagers().remove(rst);
		rst.setUserType(this);
		systemMenagers.add(rst);
	}

	public void remove(SystemMenager rst) {
		rst.setUserType(null);
		systemMenagers.remove(rst);
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

	public Set<RestaurantGuest> getRestaurantGuests() {
		return restaurantGuests;
	}

	public void setRestaurantGuests(Set<RestaurantGuest> restaurantGuests) {
		this.restaurantGuests = restaurantGuests;
	}

	public Set<RestaurantMenager> getRestaurantMenagers() {
		return restaurantMenagers;
	}

	public void setRestaurantMenagers(Set<RestaurantMenager> restaurantMenagers) {
		this.restaurantMenagers = restaurantMenagers;
	}

	public Set<SystemMenager> getSystemMenagers() {
		return systemMenagers;
	}

	public void setSystemMenagers(Set<SystemMenager> systemMenagers) {
		this.systemMenagers = systemMenagers;
	}

	public UserType(String name, Set<RestaurantGuest> restaurantGuests, Set<RestaurantMenager> restaurantMenagers,
			Set<SystemMenager> systemMenagers) {
		super();
		this.name = name;
		this.restaurantGuests = restaurantGuests;
		this.restaurantMenagers = restaurantMenagers;
		this.systemMenagers = systemMenagers;
	}

	@Override
	public String toString() {
		return "UserType [id=" + id + ", name=" + name + "]";
	}
	
	
}
