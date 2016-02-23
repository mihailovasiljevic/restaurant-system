package restaurant.server.servlet.guests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.User;
import restaurant.server.session.GenericDaoLocal;
import restaurant.server.session.ImageDaoLocal;
import restaurant.server.session.UserDaoLocal;

public class DeleteFriendController extends HttpServlet{
	
	private static final long serialVersionUID = -1426778890327680148L;
	
	@EJB
	private UserDaoLocal userDao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			req.getSession().setAttribute("infoMessage", "Morate se prijaviti!");
			resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			try {
				int id = Integer.parseInt(req.getParameter("userId"));
				User usr = userDao.findById(id);
				if (usr != null) { // da nije neko u medjuvremno obrisao
						user.removeFriend(usr);
						userDao.merge(user);
						userDao.merge(usr);
						if(req.getSession().getAttribute("friends") != null)
							req.getSession().removeAttribute("friends");
						if(req.getParameter("page").equals("guest"))
							resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
						else
							resp.sendRedirect(resp.encodeRedirectURL("../../guests/friends.jsp"));
				} else {
					System.out.println("Neko obrisao u medjuvremenu.");
					req.getSession().setAttribute("infoMessage", "taj korisnik vise ne postoji.");
					resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
				}
			} catch (Exception ex) {
				req.getSession().setAttribute("infoMessage", "Greska na serveru. Molimo pokusajte ponovo.");
				resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
			}
		}
	}

}
