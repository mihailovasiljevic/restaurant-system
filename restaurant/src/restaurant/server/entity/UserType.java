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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TYPE")
public class UserType implements Serializable{

	private static final long serialVersionUID = -6938543304472549723L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "USER_TYPE_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "USER_TYPE_NAME", nullable = false, length=64)
	private String name;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "userType")
	private Set<User> users = new HashSet<User>();
	
	public void add(User rst) {
		if (rst.getUserType() != null)
			rst.getUserType().getUsers().remove(rst);
		rst.setUserType(this);
		users.add(rst);
	}

	public void remove(User rst) {
		rst.setUserType(null);
		users.remove(rst);
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}



	public UserType(String name, Set<User> users) {
		super();
		this.name = name;
		this.users = users;
	}
	
	public UserType() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "UserType [id=" + id + ", name=" + name + "]";
	}
	
	
}
