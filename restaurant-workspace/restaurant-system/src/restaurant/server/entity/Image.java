package restaurant.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE")
public class Image implements Serializable{

	private static final long serialVersionUID = 6535667126234639820L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IMAGE_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "IMAGE_NAME",nullable = false, length=128)
	private String name;
	
	@Column(name = "IMAGE_PATH",nullable = false, length=256)
	private String path;
	
	@OneToOne(mappedBy = "image")
	private RestaurantGuest restaurantGuest;

	@OneToOne(mappedBy = "image")
	private SystemMenager systemManager;

	
	@OneToOne(mappedBy = "image")
	private RestaurantMenager restaurantMenager;


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


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public RestaurantGuest getRestaurantGuest() {
		return restaurantGuest;
	}


	public void setRestaurantGuest(RestaurantGuest restaurantGuest) {
		this.restaurantGuest = restaurantGuest;
	}


	public SystemMenager getSystemManager() {
		return systemManager;
	}


	public void setSystemManager(SystemMenager systemManager) {
		this.systemManager = systemManager;
	}


	public RestaurantMenager getRestaurantMenager() {
		return restaurantMenager;
	}


	public void setRestaurantMenager(RestaurantMenager restaurantMenager) {
		this.restaurantMenager = restaurantMenager;
	}


	public Image(Integer id, String name, String path, RestaurantGuest restaurantGuest, SystemMenager systemManager,
			RestaurantMenager restaurantMenager) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.restaurantGuest = restaurantGuest;
		this.systemManager = systemManager;
		this.restaurantMenager = restaurantMenager;
	}


	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", path=" + path + "]";
	}
	
	
}
