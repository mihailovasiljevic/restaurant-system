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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TABLES_CONFIGURATION")
@NamedQueries({
	@NamedQuery(name = "findTablesConfigurationsByUserId", query = "SELECT k FROM TablesConfiguration k WHERE k.userRestaurantMenager.id like :userId"),
})
public class TablesConfiguration implements Serializable{
	
	private static final long serialVersionUID = -9073143508246375400L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "TAB_CONF_ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "TAB_CONF_NAME", nullable = false, length=64)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TAB_CONF_DATE_FROM", nullable = false)
	private Date dateFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TAB_CONF_DATE_TO")
	private Date dateTo;

	@Column(name = "TAB_CONF_ROW_NO", nullable = false)
	private Integer numberOfRows;
	
	@Column(name = "TAB_CONF_COL_NO", nullable = false)
	private Integer numberOfCols;
	
	@Column(name = "TAB_CONF_CURR", nullable = false)
	private Boolean current;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private User userRestaurantMenager;
	
	@ManyToOne
	@JoinColumn(name = "REST_ID", referencedColumnName = "REST_ID")
	private Restaurant restaurant;
	
	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "tablesConfiguration") //mappedBy says that owning side is street
	private Set<RestaurantTable> tables = new HashSet<RestaurantTable>();
	
	public void add(RestaurantTable table) {
		if (table.getTablesConfiguration() != null)
			table.getTablesConfiguration().getTables().remove(table);
		table.setTablesConfiguration(this);
		tables.add(table);
	}

	public void remove(RestaurantTable table) {
		table.setTablesConfiguration(null);
		tables.remove(table);
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(Integer numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public Integer getNumberOfCols() {
		return numberOfCols;
	}

	public void setNumberOfCols(Integer numberOfCols) {
		this.numberOfCols = numberOfCols;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}

	public User getUserRestaurantMenager() {
		return userRestaurantMenager;
	}

	public void setUserRestaurantMenager(User userRestaurantMenager) {
		this.userRestaurantMenager = userRestaurantMenager;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Set<RestaurantTable> getTables() {
		return tables;
	}

	public void setTables(Set<RestaurantTable> tables) {
		this.tables = tables;
	}

	public TablesConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TablesConfiguration(String name, Date dateFrom, Date dateTo, Integer numberOfRows, Integer numberOfCols,
			Boolean current, User userRestaurantMenager, Restaurant restaurant, Set<RestaurantTable> tables) {
		super();
		this.name = name;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.numberOfRows = numberOfRows;
		this.numberOfCols = numberOfCols;
		this.current = current;
		this.userRestaurantMenager = userRestaurantMenager;
		this.restaurant = restaurant;
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "TablesConfiguration [id=" + id + ", name=" + name + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo
				+ ", numberOfRows=" + numberOfRows + ", numberOfCols=" + numberOfCols + ", current=" + current + "]";
	}


	
}
