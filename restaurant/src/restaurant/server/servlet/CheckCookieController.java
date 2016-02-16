package restaurant.server.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.User;
import restaurant.server.session.UserDaoLocal;

public class CheckCookieController extends HttpServlet {

	private static final long serialVersionUID = 2623661574111987641L;
	@EJB
	private UserDaoLocal userDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getSession().setAttribute("indexCounter", 1);
		if (req.getSession().getAttribute("user") == null) {
			Cookie[] cookies = req.getCookies();
			List<User> users = userDao.findBy("SELECT i.id, i.sessionId FROM User i where i.id > 0");
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("restaurant.session_id")) {
						for (User u : users) {
							if (cookies[i].getValue().equals(u.getSessionId())) {
								req.getSession().setAttribute("user", userDao.findById(u.getId()));
								resp.sendRedirect(resp.encodeRedirectURL("./index.jsp"));
							}
							break;
						}
					}
				}

			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
