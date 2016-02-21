package restaurant.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "VISIT")
@NamedQueries({
	@NamedQuery(name = "findUserVisitsNo", query = "SELECT COUNT(k) FROM Visit k WHERE k.user.id like :userId"),
	@NamedQuery(name = "findUserVisitsNoByRestaurant", query = "SELECT COUNT(k) FROM Visit k WHERE k.user.id like :userId AND k.restaurant.id like :restaurantId")

})
public class Visit implements Serializable{

	private static final long serialVersionUID = 7709612463789023979L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "VISIT_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "VISIT_GRADE", nullable = false)
	private Integer grade;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "REST_ID", referencedColumnName = "REST_ID")
	private Restaurant restaurant;
	
	@ManyToOne
	@JoinColumn(name = "RES_ID", referencedColumnName = "RES_ID")
	private Reservation reservation;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Visit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Visit(Integer grade, User user, Restaurant restaurant, Reservation reservation) {
		super();
		this.grade = grade;
		this.user = user;
		this.restaurant = restaurant;
		this.reservation = reservation;
	}

	
}
