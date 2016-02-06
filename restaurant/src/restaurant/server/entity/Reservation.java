package restaurant.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
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
@Table(name = "RESERVATION")
public class Reservation implements Serializable{

	private static final long serialVersionUID = 3847723219419666671L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "RES_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "RES_NAME", nullable = false, length=64)
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "RES_DATE", nullable = false)
	private Date date;
	
	@Column(name = "RES_FOR", nullable = false)
	private Integer forHowLong;
	
	@Column(name = "RES_GRADE", nullable = false)
	private Integer grade;
	
	@ManyToOne
	@JoinColumn(name = "REST_ID", referencedColumnName = "REST_ID")
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private User userGuestReservationMaker;
	
	@ManyToOne
	@JoinColumn(name = "REST_TABLE_ID", referencedColumnName = "REST_TABLE_ID")
	private RestaurantTable restaurantTable;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "reservation") //mappedBy says that owning side is street
	private Set<Invitation> invitations = new HashSet<Invitation>();
	
	public void add(Invitation invitation) {
		if (invitation.getReservation() != null)
			invitation.getReservation().getInvitations().remove(invitation);
		invitation.setReservation(this);
		invitations.add(invitation);
	}

	public void remove(Invitation invitation) {
		invitation.setReservation(null);
		invitations.remove(invitation);
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public Integer getForHowLong() {
		return forHowLong;
	}

	public void setForHowLong(Integer forHowLong) {
		this.forHowLong = forHowLong;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public User getUserGuestReservationMaker() {
		return userGuestReservationMaker;
	}

	public void setUserGuestReservationMaker(User userGuestReservationMaker) {
		this.userGuestReservationMaker = userGuestReservationMaker;
	}

	public RestaurantTable getRestaurantTable() {
		return restaurantTable;
	}

	public void setRestaurantTable(RestaurantTable restaurantTable) {
		this.restaurantTable = restaurantTable;
	}

	public Set<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(Set<Invitation> invitations) {
		this.invitations = invitations;
	}

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation(String name, Date date, Time time, Integer forHowLong, Integer grade, Restaurant restaurant,
			User userGuestReservationMaker, RestaurantTable restaurantTable, Set<Invitation> invitations) {
		super();
		this.name = name;
		this.date = date;

		this.forHowLong = forHowLong;
		this.grade = grade;
		this.restaurant = restaurant;
		this.userGuestReservationMaker = userGuestReservationMaker;
		this.restaurantTable = restaurantTable;
		this.invitations = invitations;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", name=" + name + ", date=" + date + ", forHowLong="
				+ forHowLong + ", grade=" + grade + ", restaurant=" + restaurant + "]";
	}
	
	
}
