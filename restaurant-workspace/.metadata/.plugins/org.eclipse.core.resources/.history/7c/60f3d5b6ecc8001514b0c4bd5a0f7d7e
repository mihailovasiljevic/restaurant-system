package restaurant.entity;
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
	@Column(name = "REST_VRST_ID", unique = true, nullable = false,columnDefinition = "CHAR(2)")
	private String id;
	
	@Column(name = "REST_VRST_NAZ", length=64)
	private String naziv;
	
	@ManyToOne
	@JoinColumn(name = "REST_VRST_ID", referencedColumnName = "REST_VRST_ID", nullable = false)
	private Restaurant restaurant;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public RestaurantType(String id, String naziv, Restaurant restaurant){
		this.id = id;
		this.naziv = naziv;
		this.restaurant = restaurant;
	}
	
	public String toString() {
		return "(Tip restorana)[id=" + id + ",naziv=" + naziv + "]";
	}
}
