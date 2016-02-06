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
	@JoinColumn(name = "USER_SEND_ID", referencedColumnName = "USER_ID")
	private User userGuestInvitationSender;
	
	@ManyToOne
	@JoinColumn(name = "USER_RCV_ID", referencedColumnName = "USER_ID")
	private User userGuestInvitationReceived;

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

	public User getUserGuestInvitationSender() {
		return userGuestInvitationSender;
	}

	public void setUserGuestInvitationSender(User userGuestInvitationSender) {
		this.userGuestInvitationSender = userGuestInvitationSender;
	}

	public User getUserGuestInvitationReceived() {
		return userGuestInvitationReceived;
	}

	public void setUserGuestInvitationReceived(User userGuestInvitationReceived) {
		this.userGuestInvitationReceived = userGuestInvitationReceived;
	}

	public Invitation(String name, Boolean invitationAccepted, Reservation reservation, User userGuestInvitationSender,
			User userGuestInvitationReceived) {
		super();
		this.name = name;
		this.invitationAccepted = invitationAccepted;
		this.reservation = reservation;
		this.userGuestInvitationSender = userGuestInvitationSender;
		this.userGuestInvitationReceived = userGuestInvitationReceived;
	}

	public Invitation() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Invitation [id=" + id + ", name=" + name + ", invitationAccepted=" + invitationAccepted
				+ ", reservation=" + reservation + "]";
	}


	
}
