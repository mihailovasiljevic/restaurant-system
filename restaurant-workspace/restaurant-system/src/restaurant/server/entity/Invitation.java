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
@Table(name = "INVITATION")
public class Invitation implements Serializable{

	private static final long serialVersionUID = -8907412337123766205L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "INV_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "INV_NAME", nullable = false, length=64)
	private String name;
	
	@Column(name = "INV_ACC", nullable = false)
	private Boolean invitationAccepted;
	
	@ManyToOne
	@JoinColumn(name = "RES_ID", referencedColumnName = "RES_ID")
	private Reservation reservation;
	
	@ManyToOne
	@JoinColumn(name = "REST_GUEST_SEND_ID", referencedColumnName = "REST_GUEST_ID")
	private RestaurantGuest restaurantSender;
	
	@ManyToOne
	@JoinColumn(name = "REST_GUEST_INV_ID", referencedColumnName = "REST_GUEST_ID")
	private RestaurantGuest restaurantInvited;

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

	public Boolean getInvitationAccepted() {
		return invitationAccepted;
	}

	public void setInvitationAccepted(Boolean invitationAccepted) {
		this.invitationAccepted = invitationAccepted;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public RestaurantGuest getRestaurantSender() {
		return restaurantSender;
	}

	public void setRestaurantSender(RestaurantGuest restaurantSender) {
		this.restaurantSender = restaurantSender;
	}

	public RestaurantGuest getRestaurantInvited() {
		return restaurantInvited;
	}

	public void setRestaurantInvited(RestaurantGuest restaurantInvited) {
		this.restaurantInvited = restaurantInvited;
	}

	public Invitation(Integer id, String name, Boolean invitationAccepted, Reservation reservation,
			RestaurantGuest restaurantSender, RestaurantGuest restaurantInvited) {
		super();
		this.id = id;
		this.name = name;
		this.invitationAccepted = invitationAccepted;
		this.reservation = reservation;
		this.restaurantSender = restaurantSender;
		this.restaurantInvited = restaurantInvited;
	}

	@Override
	public String toString() {
		return "Invitation [id=" + id + ", name=" + name + "]";
	}
	
	
}
