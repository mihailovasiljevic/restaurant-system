package restaurant.server.entity;

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
@Table(name = "DISH")
public class Dish implements Serializable{

	private static final long serialVersionUID = -742408683112477794L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "DISH_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "DISH_NAME", nullable = false, length=128)
	private String name;
	
	@Column(name = "DISH_DESC", length=512)
	private String description;
	
	@Column(name = "DISH_PRICE")
	private Double price;
	
	@ManyToOne
	@JoinColumn(name = "MENU_ID", referencedColumnName = "MENU_ID")
	private Menu menu;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Dish(String name, String description, Double price, Menu menu) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.menu = menu;
	}
	
	public Dish() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Dish [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}
	
}
