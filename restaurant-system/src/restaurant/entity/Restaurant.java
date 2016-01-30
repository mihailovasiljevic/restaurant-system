package restaurant.entity;
/**
 * 
 * @author Mihailo
 * This class represents table that is used
 * for restaurant tracking.
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

import org.apache.openjpa.jdbc.meta.strats.TimestampVersionStrategy;

import java.io.Serializable;

@Entity
@Table(name = "RESTAURANT")
public class Restaurant implements Serializable{
		
	private static final long serialVersionUID = -2100264736088386350L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_NAZ", unique = false, nullable = false, length = 128)
	private String naziv;
	
	@Column(name = "REST_OCENA", unique = false, nullable = true)
	private Integer ocena;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "gorivo")
	private Set<RestaurantType> tipoviRestorana = new HashSet<RestaurantType>();
	
	public void add(RestaurantType rst) {
		if (rst.getRestaurant() != null)
			rst.getRestaurant().getTipoviRestorana().remove(rst);
		rst.setRestaurant(this);
		tipoviRestorana.add(rst);
	}

	public void remove(RestaurantType rst) {
		rst.setRestaurant(null);
		tipoviRestorana.remove(rst);
	}

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

	public Set<RestaurantType> getTipoviRestorana() {
		return tipoviRestorana;
	}

	public void setTipoviRestorana(Set<RestaurantType> tipoviRestorana) {
		this.tipoviRestorana = tipoviRestorana;
	}

	public Restaurant(String naziv, Integer ocena, Set<RestaurantType> tipoviRestorana) {
		super();
		this.naziv = naziv;
		this.ocena = ocena;
		this.tipoviRestorana = tipoviRestorana;
	}
	
	public String toString() {
		return "(Restoran)[id=" + id + ",naziv=" + naziv + "]";
	}
	
}
