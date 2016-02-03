package restaurant.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
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
@Table(name = "RESERAVATION")
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
	
	@Temporal(TemporalType.TIME)
	@Column(name = "RES_TIME", nullable = false)
	private Time time;
	
	@Column(name = "RES_FOR", nullable = false)
	private Integer forHowLong;
	
	@Column(name = "RES_GRADE", nullable = false)
	private Integer grade;
	
	@ManyToOne
	@JoinColumn(name = "REST_ID", referencedColumnName = "REST_ID")
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "REST_GUEST_ID", referencedColumnName = "REST_GUEST_ID")
	private RestaurantGuest restaurantGuest;
	
	@ManyToOne
	@JoinColumn(name = "REST_TABLE_ID", referencedColumnName = "REST_TABLE_ID")
	private RestaurantTable restaurantTable;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "resrvation") //mappedBy says that owning side is street
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

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
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

	public RestaurantGuest getRestaurantGuest() {
		return restaurantGuest;
	}

	public void setRestaurantGuest(RestaurantGuest restaurantGuest) {
		this.restaurantGuest = restaurantGuest;
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

	public Reservation(String name, Date date, Time time, Integer forHowLong, Integer grade,
			Restaurant restaurant, RestaurantGuest restaurantGuest, RestaurantTable restaurantTable,
			Set<Invitation> invitations) {
		super();
		this.name = name;
		this.date = date;
		this.time = time;
		this.forHowLong = forHowLong;
		this.grade = grade;
		this.restaurant = restaurant;
		this.restaurantGuest = restaurantGuest;
		this.restaurantTable = restaurantTable;
		this.invitations = invitations;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", name=" + name + ", date=" + date + ", time=" + time + ", forHowLong="
				+ forHowLong + ", grade=" + grade + "]";
	}
	
	
}
