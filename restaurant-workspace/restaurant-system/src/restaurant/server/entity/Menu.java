package restaurant.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MENU")
public class Menu implements Serializable{

	private static final long serialVersionUID = -6982448549960182702L;


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MENU_ID", unique = true, nullable = false)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "MENU_DATE_FROM", nullable = false)
	private Date dateFrom;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "MENU_DATE_TO", nullable = true)
	private Date dateTo;
	
	@ManyToOne
	@JoinColumn(name = "REST_MEN_ID", referencedColumnName = "REST_MEN_ID")
	private RestaurantMenager restaurantMenager;
	
	@ManyToOne
	@JoinColumn(name = "REST_ID", referencedColumnName = "REST_ID")
	private Restaurant restaurant;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantManager") //mappedBy says that owning side is street
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

	public RestaurantMenager getRestaurantMenager() {
		return restaurantMenager;
	}

	public void setRestaurantMenager(RestaurantMenager restaurantMenager) {
		this.restaurantMenager = restaurantMenager;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Menu(Integer id, Date dateFrom, Date dateTo, RestaurantMenager restaurantMenager, Restaurant restaurant,
			Set<Dish> dishes) {
		super();
		this.id = id;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.restaurantMenager = restaurantMenager;
		this.restaurant = restaurant;
		this.dishes = dishes;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]";
	}
	
}
