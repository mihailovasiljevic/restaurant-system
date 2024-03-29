package restaurant.server.servlet.guests;

import java.io.IOException;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.User;
import restaurant.server.session.UserDaoLocal;

public class AddFriendController extends HttpServlet{
	
	private static final long serialVersionUID = -5583325395842560829L;
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
						user.addFriend(usr);
						userDao.merge(user);
						
						if(usr.getMyFriends() != null){
							Iterator<User> it = usr.getMyFriends().iterator();
							while(it.hasNext()){
								System.out.println(it.next().getId());
							}
						}
						
						if(user.getMyFriends() != null){
							Iterator<User> it = user.getMyFriends().iterator();
							while(it.hasNext()){
								System.out.println(it.next().getId());
							}
						}						
						
						if(req.getSession().getAttribute("friends") != null)
							req.getSession().removeAttribute("friends");
						if(req.getParameter("page").equals("guest"))
							resp.sendRedirect(resp.encodeRedirectURL("../../guest/guest.jsp"));
						else
							resp.sendRedirect(resp.encodeRedirectURL("../../guest/friends.jsp"));
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
