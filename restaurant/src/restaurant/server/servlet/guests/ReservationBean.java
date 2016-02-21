package restaurant.server.servlet.guests;

import java.util.ArrayList;
import java.util.Date;

import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.User;

public class ReservationBean {
	private Date date;
	private int forHowLong;
	private ArrayList<RestaurantTable> listOfTables = new ArrayList<>();
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
	public ArrayList<RestaurantTable> getListOfTables() {
		return listOfTables;
	}
	public void setListOfTables(ArrayList<RestaurantTable> listOfTables) {
		this.listOfTables = listOfTables;
	}
	public ArrayList<User> getInvitedFriends() {
		return invitedFriends;
	}
	public void setInvitedFriends(ArrayList<User> invitedFriends) {
		this.invitedFriends = invitedFriends;
	}
	public ReservationBean(Date date, int forHowLong, ArrayList<RestaurantTable> listOfTables,
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
