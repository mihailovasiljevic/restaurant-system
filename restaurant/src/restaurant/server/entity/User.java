package restaurant.server.entity;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "USER")
@NamedQuery(name = "findUserByEmail", query = "SELECT k FROM Korisnik k WHERE k.email like :userEmail")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_UD", unique = true, nullable = false)
    private Integer id;

    @Column(name = "USER_EMAIL", nullable = false, length = 80)
    private String email;

    @Column(name = "USER_PASS", nullable = false, length = 256)
    private byte[] password;


    @Column(name = "USER_SALT", nullable = false, length = 256)
    private byte[] salt;

    @Column(name = "USER_TYPE_ID", nullable = false)
    private Integer typeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public User(String email, byte[] password, Integer typeId, byte[] salt) {
        super();
        this.email = email;
        this.password = password;
        this.typeId = typeId;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
