package restaurant.server.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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
@Table(name = "RESTAURANT_TABLE")
public class RestaurantTable implements Serializable{

	private static final long serialVersionUID = 5508943516967115779L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "REST_TABLE_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "REST_TABLE_NAME", nullable = false, length=64)
	private String name;
	
	@Column(name = "REST_TABLE_ROW", nullable = false)
	private Integer row;
	
	@Column(name = "REST_TABLE_COL", nullable = false)
	private Integer col;
	
	@Column(name = "REST_TABLE_RES", nullable = false)
	private Boolean reserved = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REST_TABLE_RES_DATE")
	private Date reservedDate;
	
	@Column(name = "REST_TABLE_RES_FOR")
	private Integer reservedFor;  //for how many hours is table reserved

	@ManyToOne
	@JoinColumn(name = "TAB_CONF_ID", referencedColumnName = "TAB_CONF_ID")
	private TablesConfiguration tablesConfiguration;
	
	@ManyToMany(cascade = { ALL }, fetch = FetchType.EAGER, mappedBy="tables") //mappedBy says that owning side is street
	private Set<Reservation> reservations = new HashSet<Reservation>();
	
	public void add(Reservation res) {
		if (res.getTables() != null)
			res.getTables().remove(res);
		reservations.add(res);
	}
	public void remove(Reservation res) {
		for(Reservation r : reservations){
			if(r.getId().equals(res.getId())){
				reservations.remove(r);
				break;
			}
		}
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

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCol() {
		return col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	public Boolean getReserved() {
		return reserved;
	}

	public void setReserved(Boolean reserved) {
		this.reserved = reserved;
	}

	public Date getReservedDate() {
		return reservedDate;
	}

	public void setReservedDate(Date reservedDate) {
		this.reservedDate = reservedDate;
	}

	public Integer getReservedFor() {
		return reservedFor;
	}

	public void setReservedFor(Integer reservedFor) {
		this.reservedFor = reservedFor;
	}

	public TablesConfiguration getTablesConfiguration() {
		return tablesConfiguration;
	}

	public void setTablesConfiguration(TablesConfiguration tablesConfiguration) {
		this.tablesConfiguration = tablesConfiguration;
	}

	public Set<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(Set<Reservation> reservations) {
		this.reservations = reservations;
	}

	public RestaurantTable( String name, Integer row, Integer col, Boolean reserved, Date reservedDate,
			Integer reservedFor, TablesConfiguration tablesConfiguration,
			Set<Reservation> reservations) {
		super();
		this.name = name;
		this.row = row;
		this.col = col;
		this.reserved = reserved;
		this.reservedDate = reservedDate;
		this.reservedFor = reservedFor;
		this.tablesConfiguration = tablesConfiguration;
		this.reservations = reservations;
	}
	
	public RestaurantTable() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "RestaurantTable [id=" + id + ", row=" + row + ", col=" + col + ", reserved=" + reserved
				+ ", reservedFor=" + reservedFor + "]";
	}
	
	
}
