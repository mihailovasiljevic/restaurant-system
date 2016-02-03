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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANT_MENAGER")
public class RestaurantMenager implements Serializable{

	private static final long serialVersionUID = 6002506091008637872L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_MEN_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_MEN_NAME", nullable = false, length=64)
	private String name;
	
	@Column(name = "REST_MEN_SURNAME", nullable = false, length = 64)
	private String surname;
	
	@Column(name = "REST_MEN_EMAIL", nullable = false, length = 80)
	private String email;
	
	@Column(name = "REST_MEN_PASS", nullable = false, length = 256)
	private byte[] password;

	@Column(name = "REST_MEN_SALT", nullable = false, length = 256)
	private byte[] salt;

	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
	private Address address;
	
	@OneToOne
	@JoinColumn(name = "IMAGE_ID", referencedColumnName = "IMAGE_ID")
	private Image image;
	
	@ManyToOne
	@JoinColumn(name = "SYS_MEN_ID", referencedColumnName = "SYS_MEN_ID")
	private SystemMenager systemMenager;
	
	@ManyToOne
	@JoinColumn(name = "USER_TYPE_ID", referencedColumnName = "USER_TYPE_ID")
	private UserType userType;

	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantManager") //mappedBy says that owning side is street
	private Set<TablesConfiguration> tablesConfigurations = new HashSet<TablesConfiguration>();
	
	public void add(TablesConfiguration tablesConfiguration) {
		if (tablesConfiguration.getRestaurantMenager() != null)
			tablesConfiguration.getRestaurantMenager().getTablesConfigurations().remove(tablesConfiguration);
		tablesConfiguration.setRestaurantMenager(this);
		tablesConfigurations.add(tablesConfiguration);
	}

	public void remove(TablesConfiguration tablesConfiguration) {
		tablesConfiguration.setRestaurantMenager(null);
		tablesConfigurations.remove(tablesConfiguration);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantManager") //mappedBy says that owning side is street
	private Set<Menu> menus = new HashSet<Menu>();
	
	public void add(Menu menu) {
		if (menu.getRestaurantMenager() != null)
			menu.getRestaurantMenager().getMenus().remove(menu);
		menu.setRestaurantMenager(this);
		menus.add(menu);
	}

	public void remove(Menu menu) {
		menu.setRestaurantMenager(null);
		menus.remove(menu);
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public SystemMenager getSystemMenager() {
		return systemMenager;
	}

	public void setSystemMenager(SystemMenager systemMenager) {
		this.systemMenager = systemMenager;
	}

	public Set<TablesConfiguration> getTablesConfigurations() {
		return tablesConfigurations;
	}

	public void setTablesConfigurations(Set<TablesConfiguration> tablesConfigurations) {
		this.tablesConfigurations = tablesConfigurations;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public RestaurantMenager(String name, String surname, String email, byte[] password, Address address, Image image,
							 SystemMenager systemMenager, UserType userType, Set<TablesConfiguration> tablesConfigurations,
							 Set<Menu> menus, byte[] salt) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.image = image;
		this.systemMenager = systemMenager;
		this.userType = userType;
		this.tablesConfigurations = tablesConfigurations;
		this.menus = menus;
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "RestaurantMenager [id=" + id + ", name=" + name + ", surname=" + surname + "]";
	}
	
	
}
