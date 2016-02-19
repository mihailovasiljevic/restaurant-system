package restaurant.server.servlet.dishes;

public class DishBean {
	private String name;
	private String description;
	private Double price;

	public DishBean() {
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

	public DishBean(String name, String description, Double price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}

}
