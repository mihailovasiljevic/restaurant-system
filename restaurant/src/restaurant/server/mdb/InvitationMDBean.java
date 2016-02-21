package restaurant.server.mdb;

import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import restaurant.server.entity.Invitation;
import restaurant.server.entity.Reservation;
import restaurant.server.entity.User;

@MessageDriven(
		activationConfig = {
			@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "InvitationQueue") 
		}
	)
public class InvitationMDBean implements MessageListener{
	
	@Resource(name="Mail")
	Session session;
	
	public InvitationMDBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
	    try {
	        if (message instanceof ObjectMessage) {
	            ObjectMessage obj = (ObjectMessage) message;
	            User user = (User)obj.getObject();
	            
	            // Validate the credit card using web services...
	            
	            sendMessage(user);
	        } else {
	            System.out.println("MESSAGE BEAN: Message of wrong type: " + message.getClass().getName());
	        }
	    } catch (JMSException e) {
	        e.printStackTrace();
	    } catch (Throwable te) {
	        te.printStackTrace();
	    }
	}
	
	private void sendMessage(User user) throws AddressException, MessagingException {


		Iterator<Reservation> iterator = user.getReservations().iterator(); 
		//preuzmi poslednje napravljenu rezervaciju
		Reservation lastReservation = new Reservation();
		while(iterator.hasNext()){
			if(!iterator.hasNext()) {
				lastReservation = ((Reservation)iterator);
			}
		}
		
		Iterator<Invitation> invitation = lastReservation.getInvitations().iterator(); 
		//preuzmi sve pozvnae korisnike i poslji im pozivnice
		while(invitation.hasNext()){
			// Constructs the message 
			javax.mail.Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("authorit0mv@gmail.com"));
			
			msg.setSubject("Potvrda rezervacije - pozivnica");
			StringBuilder sb = new StringBuilder();
			sb.append("");
			sb.append("Ovo je automatski generisana poruka.\n");
			sb.append("Vas prijatelj: "+invitation.next().getUserGuestInvitationSender().getName()+" "+invitation.next().getUserGuestInvitationSender().getSurname()+" vas je pozvao da prisustvejete rezervaciji:\n");
			sb.append("Restoran: " + invitation.next().getReservation().getRestaurant().getName());
			sb.append("\nDatum i vreme: "+ invitation.next().getReservation().getDate());
			sb.append("\nDuzina trajanja rezervacije: " + invitation.next().getReservation().getForHowLong()+"\n");
			sb.append("Posetitet stranicu ispod kako biste potvrdili vas dolazak.\n");
			sb.append("http://localhost:8080/restaurant/invitation?userId="+invitation.next().getUserGuestInvitationReceived().getId()+"&reservation="+invitation.next().getReservation().getId());
			msg.setText(sb.toString());
			msg.setRecipients(RecipientType.TO, InternetAddress.parse(invitation.next().getUserGuestInvitationReceived().getEmail()));
			msg.setSentDate(new Date());
			// Sends the message
			Transport.send(msg);
			System.out.println("MESSAGE BEAN: Mail was sent successfully.");
		}
		
		

		
;

		
	}
}
