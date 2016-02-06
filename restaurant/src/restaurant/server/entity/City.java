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
@Table(name = "CITY")
public class City implements Serializable{

	private static final long serialVersionUID = 9085657941969873057L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CITY_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "CITY_NAME", nullable = false, length=64, unique = true)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "CNT_ID", referencedColumnName = "CNT_ID")
	private Country country;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "city") //mappedBy says that owning side is city
	private Set<Street> streets = new HashSet<Street>();
	
	public void add(Street street) {
		if (street.getCity() != null)
			street.getCity().getStreets().remove(street);
		street.setCity(this);
		streets.add(street);
	}

	public void remove(Street street) {
		street.setCity(null);
		streets.remove(street);
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<Street> getStreets() {
		return streets;
	}

	public void setStreets(Set<Street> streets) {
		this.streets = streets;
	}

	public City(String name, Country country, Set<Street> streets) {
		super();
		this.name = name;
		this.country = country;
		this.streets = streets;
	}
	
	public City() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "City [id=" + id + ", naziv=" + name + "]";
	}
	
	
}
