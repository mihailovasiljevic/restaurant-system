package restaurant.server.servlet.restaurants;
import java.util.*;
public class RestaurantBean {
	private String name;
	private Integer type;
	private Integer street;
	private String streetNo;
	private List<Integer> menagers = new ArrayList<>();
	public RestaurantBean(){}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStreet() {
		return street;
	}
	public void setStreet(Integer street) {
		this.street = street;
	}
	public String getStreetNo() {
		return streetNo;
	}
	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}
	
	public List<Integer> getMenagers() {
		return menagers;
	}
	public void setMenagers(List<Integer> menagers) {
		this.menagers = menagers;
	}
	public RestaurantBean(String name, Integer type, Integer street, String streetNo, List<Integer> menagers) {
		super();
		this.name = name;
		this.type = type;
		this.street = street;
		this.streetNo = streetNo;
		this.menagers = menagers;
	}
	
}
