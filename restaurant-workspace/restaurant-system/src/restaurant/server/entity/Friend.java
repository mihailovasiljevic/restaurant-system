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
@Table(name = "FRIEND")
public class Friend implements Serializable{

	private static final long serialVersionUID = -1358626923968173789L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "FRIEND_ID", unique = true, nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "REST_GUEST_ID", referencedColumnName = "REST_GUEST_ID")
	private RestaurantGuest restaurantGuest;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RestaurantGuest getRestaurantGuest() {
		return restaurantGuest;
	}

	public void setRestaurantGuest(RestaurantGuest restaurantGuest) {
		this.restaurantGuest = restaurantGuest;
	}


	public Friend(Integer id, RestaurantGuest restaurantGuest, RestaurantGuest restaurantFriend) {
		super();
		this.id = id;
		this.restaurantGuest = restaurantGuest;
	}

	@Override
	public String toString() {
		return "Friend [id=" + id + ", restaurantGuest=" + restaurantGuest.getName() 
				+ "]";
	}
	
	
}
