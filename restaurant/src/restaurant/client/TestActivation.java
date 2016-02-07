package restaurant.client;


import java.util.List;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import restaurant.server.entity.User;
import restaurant.server.session.ActivateAccount;

import restaurant.server.session.UserDaoLocal;



public class TestActivation {
	
	@EJB
	private static UserDaoLocal udal;
	
	public static void main(String[] args) {

		try {
			Context ctx = new InitialContext();

			ActivateAccount activate = (ActivateAccount) ctx.lookup("ActivateAccountBeanRemote");
			
			List<User> users = udal.findAll();
			System.out.println("LISTA.SIZE: " + users.size());
			
			activate.activate(users.get(6));

		} catch (NamingException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
