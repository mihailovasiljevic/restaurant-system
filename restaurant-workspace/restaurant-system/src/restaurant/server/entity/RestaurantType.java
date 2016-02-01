package restaurant.server.entity;
/**
 * 
 * @author Mihailo
 * This class represents table that is used
 * for restaurant clasification.
 */

/**
 * CascadeType.ALL means that when persisting, removing, refreshing or merging entity/ entity state 
 * EntityManager does that for all entities held in this field.
 * It is shortcut for all operations: PERSIST, REMOVE, REFRESH and MERGE.
 */
import static javax.persistence.CascadeType.ALL;
/**
 * FetchType.LAZY is used when you want
 * to defer loading of the field until you access it.
 * When you attempt to lazy load the field for the first ttime, the JPA runtime will 
 * the JPA runtime will load the value from database and populate the field automatically.
 * It is only a hint not a directive, because some JPA implementations,
 * cannot lazy-load certain field types.
 * 
 * FetchType.EAGER means that the field is loaded by the JPA
 * implementation before it returns the persistent object to you.
 * That means that all of the fields are populated with database data,
 * when you retrieve entity from a query or EntityManager.
 */
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
@Table(name = "RESTAURANT_TYPE")
public class RestaurantType implements Serializable{
	
	private static final long serialVersionUID = 3263127309803325049L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_TYPE_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_TYPE_NAME",nullable = false, length=64)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "SYS_MEN_ID", referencedColumnName = "SYS_MEN_ID", nullable = false)
	private SystemMenager systemMenager;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantType")
	private Set<Restaurant> restaurants = new HashSet<Restaurant>();
	
	public void add(Restaurant rst) {
		if (rst.getRestaurantType() != null)
			rst.getRestaurantType().getRestaurants().remove(rst);
		rst.setRestaurantType(this);
		restaurants.add(rst);
	}

	public void remove(Restaurant rst) {
		rst.setRestaurantType(null);
		restaurants.remove(rst);
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

	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public SystemMenager getSystemMenager() {
		return systemMenager;
	}

	public void setSystemMenager(SystemMenager systemMenager) {
		this.systemMenager = systemMenager;
	}

	public RestaurantType(Integer id, String name, SystemMenager systemMenager, Set<Restaurant> restaurants) {
		super();
		this.id = id;
		this.name = name;
		this.systemMenager = systemMenager;
		this.restaurants = restaurants;
	}

	public String toString() {
		return "(Tip restorana)[id=" + id + ",naziv=" + name + "]";
	}
}
