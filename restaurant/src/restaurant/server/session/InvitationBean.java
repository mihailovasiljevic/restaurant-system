package restaurant.server.session;

import java.util.HashMap;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import restaurant.server.entity.User;

@Stateless
@Local(InvitationLocal.class)
@Remote(Invitation.class)
public class InvitationBean implements Invitation{
	@Resource(name="JmsConnectionFactory")
	private ConnectionFactory qcf;

	@Resource(name="InvitationQueue")
	private Queue invitationQueue;
	
	@Override
	public void invitate(HashMap<Integer, User> reserve) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		
		try {
			// Creates a connection
			Properties propeties=new Properties();
			propeties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			propeties.put(Context.PROVIDER_URL, "tcp://"+"localhost"+":61616");
			try {
				qcf=(ConnectionFactory) (new InitialContext(propeties)).lookup("ConnectionFactory");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection = qcf.createConnection();

            // Creates a session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Creates a message producer from the Session to the Topic or Queue
            producer = session.createProducer(invitationQueue);

            // Creates an object message
            ObjectMessage object = session.createObjectMessage();
            object.setObject(reserve);
		    
            // Tells the producer to send the object message
            producer.send(object);
            
            // Closes the producer
            producer.close();
            
            // Closes the session
            session.close();
            
            // Closes the connection
            connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
