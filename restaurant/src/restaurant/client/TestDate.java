package restaurant.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import restaurant.server.entity.Reservation;

public class TestDate {
	public static void main(String args[]){
		Date date;
		Date date2;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    	String dateInString = "12-3-20015 11:10:00";
    	String dateInString2 = "12-3-20015 15:10:00";
		try {
			date = sdf.parse(dateInString);
			date2 = sdf.parse(dateInString2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println(date.compareTo(date));
		System.out.println(date.compareTo(date2));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		if(minute > 30)
			hour++;
		int howLong = 2;
		int howLong2 = 3;
		
		Calendar cal2 = Calendar.getInstance();
		cal.setTime(date2);
		int hour2 = cal.get(Calendar.HOUR_OF_DAY);
		int minute2 = cal.get(Calendar.MINUTE);
		if(minute2 > 30)
			hour2++;

	}
}
