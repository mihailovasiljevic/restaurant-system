package restaurant.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESTAURANT_GUEST")
public class RestaurantGuest implements Serializable{

	private static final long serialVersionUID = -97243651610451388L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_GUEST_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_GUEST_NAME", nullable = false, length=64)
	private String name;
	
	@Column(name = "REST_GUEST_SURNAME", nullable = false, length = 64)
	private String surname;
	
	@Column(name = "REST_GUEST_EMAIL", nullable = false, length = 80)
	private String email;
	
	@Column(name = "REST_GUEST_PASS", nullable = false, length = 256)
	private String password;
	
	@Column(name = "REST_GUEST_ACT", nullable = false)
	private Boolean activated;
	
	@Column(name = "REST_GUEST_VIS_NO", nullable = false)
	private Integer numberOfVisits;
	
	@ManyToOne
	@JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
	private Address address;
	
	@ManyToOne
	@JoinColumn(name = "REST_ID", referencedColumnName = "REST_ID")
	private Restaurant restaurant;
	
	@OneToOne
	@JoinColumn(name = "IMAGE_ID", referencedColumnName = "IMAGE_ID")
	private Image image;
	
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantGuest") //mappedBy says that owning side is street
	private Set<Friend> myFriends = new HashSet<Friend>();
	
	public void add(Friend friend) {
		if (friend.getRestaurantGuest() != null)
			friend.getRestaurantGuest().getMyFriends().remove(friend);
		friend.setRestaurantGuest(this);
		myFriends.add(friend);
	}

	public void remove(Friend friend) {
		friend.setRestaurantGuest(null);
		myFriends.remove(friend);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantGuest") //mappedBy says that owning side is street
	private Set<FriendTo> iAmFriendTo = new HashSet<FriendTo>();
	
	public void add(FriendTo friend) {
		if (friend.getRestaurantGuest() != null)
			friend.getRestaurantGuest().getiAmFriendTo().remove(friend);
		friend.setRestaurantGuest(this);
		iAmFriendTo.add(friend);
	}

	public void remove (FriendTo friend) {
		friend.setRestaurantGuest(null);
		iAmFriendTo.remove(friend);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantSender") //mappedBy says that owning side is street
	private Set<Invitation> sentInvitations = new HashSet<Invitation>();
	
	public void add(Invitation invitation) {
		if (invitation.getRestaurantSender() != null)
			invitation.getRestaurantSender().getSentInvitations().remove(invitation);
		invitation.setRestaurantSender(this);
		sentInvitations.add(invitation);
	}

	public void remove(Invitation invitation) {
		invitation.setRestaurantSender(null);
		sentInvitations.remove(invitation);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantInvited") //mappedBy says that owning side is street
	private Set<Invitation> receivedInvitations = new HashSet<Invitation>();
	
	public void addInv(Invitation invitation) {
		if (invitation.getRestaurantInvited() != null)
			invitation.getRestaurantInvited().getReceivedInvitations().remove(invitation);
		invitation.setRestaurantInvited(this);
		receivedInvitations.add(invitation);
	}

	public void removeInv(Invitation invitation) {
		invitation.setRestaurantInvited(null);
		receivedInvitations.remove(invitation);
	}
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "restaurantGuest") //mappedBy says that owning side is street
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	public void add(Reservation reservation) {
		if (reservation.getRestaurantGuest() != null)
			reservation.getRestaurantGuest().getReservations().remove(reservation);
		reservation.setRestaurantGuest(this);
		reservations.add(reservation);
	}

	public void remove(Reservation reservation) {
		reservation.setRestaurantGuest(null);
		reservations.remove(reservation);
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Integer getNumberOfVisits() {
		return numberOfVisits;
	}

	public void setNumberOfVisits(Integer numberOfVisits) {
		this.numberOfVisits = numberOfVisits;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Set<Friend> getMyFriends() {
		return myFriends;
	}

	public void setMyFriends(Set<Friend> myFriends) {
		this.myFriends = myFriends;
	}

	public Set<FriendTo> getiAmFriendTo() {
		return iAmFriendTo;
	}

	public void setiAmFriendTo(Set<FriendTo> iAmFriendTo) {
		this.iAmFriendTo = iAmFriendTo;
	}

	public Set<Invitation> getSentInvitations() {
		return sentInvitations;
	}

	public void setSentInvitations(Set<Invitation> sentInvitations) {
		this.sentInvitations = sentInvitations;
	}

	public Set<Invitation> getReceivedInvitations() {
		return receivedInvitations;
	}

	public void setReceivedInvitations(Set<Invitation> receivedInvitations) {
		this.receivedInvitations = receivedInvitations;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public RestaurantGuest(Integer id, String name, String surname, String email, String password, Boolean activated,
			Integer numberOfVisits, Address address, Restaurant restaurant, Image image, Set<Friend> myFriends,
			Set<FriendTo> iAmFriendTo, Set<Invitation> sentInvitations, Set<Invitation> receivedInvitations,
			Set<Reservation> reservations) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.activated = activated;
		this.numberOfVisits = numberOfVisits;
		this.address = address;
		this.restaurant = restaurant;
		this.image = image;
		this.myFriends = myFriends;
		this.iAmFriendTo = iAmFriendTo;
		this.sentInvitations = sentInvitations;
		this.receivedInvitations = receivedInvitations;
		this.reservations = reservations;
	}

	@Override
	public String toString() {
		return "RestaurantGuest [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email
				+ ", activated=" + activated + "]";
	}
	
	
}
