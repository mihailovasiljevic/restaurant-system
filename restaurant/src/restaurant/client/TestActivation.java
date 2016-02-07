package restaurant.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import restaurant.server.entity.User;
import restaurant.server.session.ActivateAccount;
import restaurant.server.session.UserDaoBean;
import restaurant.server.session.UserDaoLocal;


public class TestActivation {
	public static void main(String[] args) {

		try {
			Context ctx = new InitialContext();

			ActivateAccount activate = (ActivateAccount) ctx.lookup("ActivateAccountBeanRemote");
			
			UserDaoLocal udal = (UserDaoBean)ctx.lookup("UserDaoBeanLocal");
			User user = udal.findById(3);
			
			activate.activate(user);

		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
