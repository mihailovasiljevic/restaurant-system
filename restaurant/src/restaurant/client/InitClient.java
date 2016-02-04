package restaurant.client;

import restaurant.server.session.Init;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Misa on 04.02.2016..
 */
public class InitClient {
    public static void main(String args[]) {

        try {
            Context ctx = new InitialContext();
            Init init = (Init) ctx.lookup("InitBeanRemote");
            init.init();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
