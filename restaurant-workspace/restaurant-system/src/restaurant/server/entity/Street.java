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
@Table(name = "STREET")
public class Street implements Serializable{

	private static final long serialVersionUID = -4380966825021692227L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "STREET_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "STREET_NAME", nullable = false, length=64)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "CITY_ID", referencedColumnName = "CITY_ID", nullable = false)
	private City city;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "street") //mappedBy says that owning side is street
	private Set<Address> addresses = new HashSet<Address>();
	
	public void add(Address address) {
		if (address.getStreet() != null)
			address.getStreet().getAddresses().remove(address);
		address.setStreet(this);
		addresses.add(address);
	}

	public void remove(Address address) {
		address.setStreet(null);
		addresses.remove(address);
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Street(Integer id, String name, City city, Set<Address> addresses) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Street [id=" + id + ", naziv=" + name + "]";
	}
	
	
}
