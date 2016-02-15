package restaurant.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MENU")
@NamedQueries({
	@NamedQuery(name = "findMenusByUserId", query = "SELECT k FROM Menu k WHERE k.userRestaurantMenager.id like :userId"),
})
public class Menu implements Serializable{

	private static final long serialVersionUID = -6982448549960182702L;


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MENU_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "MENU_NAME", nullable = false, length=64)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MENU_DATE_FROM", nullable = false)
	private Date dateFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MENU_DATE_TO")
	private Date dateTo;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private User userRestaurantMenager;
	
	@ManyToOne
	@JoinColumn(name = "REST_ID", referencedColumnName = "REST_ID")
	private Restaurant restaurant;
	
	@Column(name = "MENU_CURR", nullable = true) //nullable dok se ne promeni  insert dao bean
	private Boolean current;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "menu") //mappedBy says that owning side is street
	private Set<Dish> dishes = new HashSet<Dish>();
	
	public void add(Dish dish) {
		if (dish.getMenu() != null)
			dish.getMenu().getDishes().remove(dish);
		dish.setMenu(this);
		dishes.add(dish);
	}

	public void remove(Dish dish) {
		dish.setMenu(null);
		dishes.remove(dish);
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public User getUserRestaurantMenager() {
		return userRestaurantMenager;
	}

	public void setUserRestaurantMenager(User userRestaurantMenager) {
		this.userRestaurantMenager = userRestaurantMenager;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Set<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(Set<Dish> dishes) {
		this.dishes = dishes;
	}

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}

	public Menu(String name, Date dateFrom, Date dateTo, User userRestaurantMenager, Restaurant restaurant,
			Set<Dish> dishes, Boolean current) {
		super();
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.userRestaurantMenager = userRestaurantMenager;
		this.restaurant = restaurant;
		this.dishes = dishes;
		this.current = current;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
	}

}
