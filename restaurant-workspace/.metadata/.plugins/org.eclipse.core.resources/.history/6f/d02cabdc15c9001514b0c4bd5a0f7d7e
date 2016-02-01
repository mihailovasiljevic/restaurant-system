package restaurant.server.entity;
/**
 * 
 * @author Mihailo
 * This class represents table that is used
 * for restaurant tracking.
 */

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
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANT")
public class Restaurant implements Serializable{
		
	private static final long serialVersionUID = -2100264736088386350L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_NAZ", nullable = false, length = 128)
	private String naziv;
	
	@Column(name = "REST_OCENA", nullable = true)
	private Integer ocena;
	
	@ManyToOne
	@JoinColumn(name = "REST_VRST_ID", referencedColumnName = "REST_VRST_ID", nullable = false)
	private RestaurantType restaurantType;
	
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID", nullable = false)
	private Address address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Integer getOcena() {
		return ocena;
	}

	public void setOcena(Integer ocena) {
		this.ocena = ocena;
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
	
	public Restaurant(Integer id, String naziv, Integer ocena, RestaurantType restaurantType, Address address) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.ocena = ocena;
		this.restaurantType = restaurantType;
		this.address = address;
	}

	public String toString() {
		return "(Restoran)[id=" + id + ",naziv=" + naziv + "]";
	}
	
}
