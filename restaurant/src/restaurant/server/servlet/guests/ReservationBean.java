package restaurant.server.servlet.guests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;

public class ReservationBean {
	private Date date;
	private int forHowLong;
	private TablesConfiguration conf;
	private List<RestaurantTable> listOfTables = new ArrayList<>();
	private ArrayList<User> invitedFriends;

	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getForHowLong() {
		return forHowLong;
	}
	public void setForHowLong(int forHowLong) {
		this.forHowLong = forHowLong;
	}
	public List<RestaurantTable> getListOfTables() {
		return listOfTables;
	}
	public void setListOfTables(List<RestaurantTable> tablesFromRestaurant) {
		this.listOfTables = tablesFromRestaurant;
	}
	public ArrayList<User> getInvitedFriends() {
		return invitedFriends;
	}
	public void setInvitedFriends(ArrayList<User> invitedFriends) {
		this.invitedFriends = invitedFriends;
	}
	
	public TablesConfiguration getConf() {
		return conf;
	}
	public void setConf(TablesConfiguration conf) {
		this.conf = conf;
	}
	public ReservationBean(Date date, int forHowLong, List<RestaurantTable> listOfTables,
			ArrayList<User> invitedFriends) {
		this.date = date;
		this.forHowLong = forHowLong;
		this.listOfTables = listOfTables;
		this.invitedFriends = invitedFriends;
	}
	public ReservationBean() {
		// TODO Auto-generated constructor stub
	}
	
	
}
