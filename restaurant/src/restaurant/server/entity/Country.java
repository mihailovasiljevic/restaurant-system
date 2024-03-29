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
@Table(name = "COUNTRY")
public class Country implements Serializable{
	
	private static final long serialVersionUID = -3468522740832866531L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CNT_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "CNT_NAME",nullable = false, length=64, unique = true)
	private String name;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "country") //mappedBy says that owning side is country
	private Set<City> cities = new HashSet<City>();
	
	public void add(City city) {
		if (city.getCountry() != null)
			city.getCountry().getCities().remove(city);
		city.setCountry(this);
		cities.add(city);
	}

	public void remove(City city) {
		city.setCountry(null);
		cities.remove(city);
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

	public Set<City> getCities() {
		return cities;
	}

	public void setCities(Set<City> cities) {
		this.cities = cities;
	}
	
	public Country() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Country(String name, Set<City> cities) {
		super();
		this.name = name;
		this.cities = cities;
	}
	
	
}
