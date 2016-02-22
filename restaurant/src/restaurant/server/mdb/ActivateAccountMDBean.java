package restaurant.server.mdb;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import restaurant.server.entity.User;


@MessageDriven(
		activationConfig = {
			@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
			@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
			@ActivationConfigProperty(propertyName = "destination", propertyValue = "ActivationQueue") 
		}
	)
public class ActivateAccountMDBean implements MessageListener{
	@Resource(name="Mail")
	Session session;
	
	public ActivateAccountMDBean() {
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

		// Constructs the message 
		javax.mail.Message msg = new MimeMessage(session);
		
		msg.setFrom(new InternetAddress("authorit0mv@gmail.com"));
		msg.setRecipients(RecipientType.TO, InternetAddress.parse(user.getEmail()));
		msg.setSubject("Aktivacija naloga - sistem restorana");
		StringBuilder sb = new StringBuilder();
		sb.append("Ovo je automatski generisana poruka.Postovani, " + user.getName()+" " + user.getSurname()+" molimo vas da potvrdite registraciju pritiskom na link ispod.");
		sb.append("<br />http://localhost:8080/restaurant/activate?userId="+user.getId()+"&activate=true");
		msg.setText(sb.toString());
		msg.setSentDate(new Date());
		
		// Sends the message
		Transport.send(msg);

		System.out.println("MESSAGE BEAN: Mail was sent successfully.");
	}

}
