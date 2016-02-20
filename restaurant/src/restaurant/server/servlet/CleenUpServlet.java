package restaurant.server.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.User;
import restaurant.server.session.UserDaoLocal;

public class CleenUpServlet extends HttpServlet{
	@EJB
	private UserDaoLocal userDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User)req.getSession().getAttribute("user");
		Iterator<User> it = user.getMyFriends().iterator();
		while(it.hasNext()){
			it.next().setMyFriends(new HashSet<User>());
		}
		user.setMyFriends(new HashSet<User>());
		userDao.merge(user);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
}
