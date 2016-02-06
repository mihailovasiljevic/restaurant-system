package restaurant.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private User userGuest;
	
	@OneToOne
	@JoinColumn(name = "USER_OWNER_ID", referencedColumnName = "USER_ID")
	private User friendshipOwner;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUserGuest() {
		return userGuest;
	}

	public void setUserGuest(User userGuest) {
		this.userGuest = userGuest;
	}

	public User getFriendshipOwner() {
		return friendshipOwner;
	}

	public void setFriendshipOwner(User friendshipOwner) {
		this.friendshipOwner = friendshipOwner;
	}

	public Friend() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Friend(User userGuest, User friendshipOwner) {
		super();
		this.id = id;
		this.userGuest = userGuest;
		this.friendshipOwner = friendshipOwner;
	}


	@Override
	public String toString() {
		return "Friend [id=" + id + "]";
	}
	
	
}
