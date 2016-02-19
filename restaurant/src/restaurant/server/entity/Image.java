package restaurant.server.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGE")
public class Image implements Serializable{

	private static final long serialVersionUID = 6535667126234639820L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IMAGE_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "IMAGE_NAME",nullable = false, length=80)
	private byte[] name;
	
	@Column(name = "IMAGE_REAL_NAME",nullable = false, length=256, unique = true)
	private String realName;
	
	@Column(name = "IMAGE_PATH",nullable = false, length=512)
	private String path;
	
	@OneToOne(mappedBy = "image")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getName() {
		return name;
	}

	public void setName(byte[] uploadImageHashedName) {
		this.name = uploadImageHashedName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Image(byte[] name, String path, User user, String realName) {
		super();
		this.name = name;
		this.path = path;
		this.user = user;
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", path=" + path + "]";
	}
	
}
