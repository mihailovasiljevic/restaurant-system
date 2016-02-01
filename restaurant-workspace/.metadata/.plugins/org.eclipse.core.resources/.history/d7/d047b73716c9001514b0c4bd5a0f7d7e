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
	
	@Column(name = "CITY_NAZ", nullable = false, length=64)
	private String naziv;
	
	@ManyToOne
	@JoinColumn(name = "CNT_ID", referencedColumnName = "CNT_ID", nullable = false)
	private Country country;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "CITY") //mappedBy says that owning side is city
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

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
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

	public City(Integer id, String naziv, Country country, Set<Street> streets) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.country = country;
		this.streets = streets;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", naziv=" + naziv + "]";
	}
	
	
}
