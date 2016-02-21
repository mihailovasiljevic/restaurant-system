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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@Temporal(TemporalType.TIMESTAMP)
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
	
	@ManyToMany(cascade = { ALL }, fetch = FetchType.EAGER) //mappedBy says that owning side is street
	@JoinTable(
		      name="RESERVED_TABLE",
		      joinColumns=@JoinColumn(name="RES_ID", referencedColumnName="RES_ID"),
		      inverseJoinColumns=@JoinColumn(name="REST_TABLE_ID", referencedColumnName="REST_TABLE_ID"))
	private Set<RestaurantTable> tables = new HashSet<RestaurantTable>();
	
	public void add(RestaurantTable table) {
		if (table.getReservations() != null)
			table.getReservations().remove(table);
		tables.add(table);
	}
	public void remove(RestaurantTable table) {
		for(RestaurantTable r : tables){
			if(r.getId().equals(table.getId())){
				tables.remove(r);
				break;
			}
		}
	}

	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "reservation") //mappedBy says that owning side is street
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
	
	
	@OneToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy = "reservation") //mappedBy says that owning side is street
	private Set<Visit> visits = new HashSet<Visit>();
	
	public void add(Visit visit) {
		if (visit.getReservation() != null)
			visit.getReservation().getVisits().remove(visit);
		visit.setReservation(this);
		visits.add(visit);
	}

	public void remove(Visit visit) {
		visit.setReservation(null);
		visits.remove(visit);
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


	public Set<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(Set<Invitation> invitations) {
		this.invitations = invitations;
	}

	

	public Set<RestaurantTable> getTables() {
		return tables;
	}
	public void setTables(Set<RestaurantTable> tables) {
		this.tables = tables;
	}
	public Set<Visit> getVisits() {
		return visits;
	}

	public void setVisits(Set<Visit> visits) {
		this.visits = visits;
	}

	public Reservation() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Reservation(String name, Date date, Integer forHowLong, Integer grade, Restaurant restaurant,
			User userGuestReservationMaker, Set<Invitation> invitations,
			Set<Visit> visits) {
		super();
		this.name = name;
		this.date = date;
		this.forHowLong = forHowLong;
		this.grade = grade;
		this.restaurant = restaurant;
		this.userGuestReservationMaker = userGuestReservationMaker;
		this.invitations = invitations;
		this.visits = visits;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", name=" + name + ", date=" + date + ", forHowLong="
				+ forHowLong + ", grade=" + grade + ", restaurant=" + restaurant + "]";
	}
	
	
}
