package restaurant.server.servlet.restaurantMenagers;

import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;
import restaurant.server.session.*;

public class DeleteRestaurantMenagerController extends HttpServlet{
	@EJB
	private UserDaoLocal userDao;
	@EJB
	private ImageDaoLocal imageDao;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getSession().getAttribute("user") == null) {
			System.out.println("Nema korisnika na sesiji");
			resp.sendRedirect(resp.encodeRedirectURL("../../login.jsp"));
			return;
		} else {
			User user = (User) req.getSession().getAttribute("user");
			System.out.println("User type: " + user.getUserType().getName());
			if (!(user.getUserType().getName()).equals("SYSTEM_MENAGER")) {
				System.out.println("Korisnik nije menadzer sistema i nema ovlascenja da uradi tako nesto!");
				resp.sendRedirect(resp.encodeRedirectURL("../../insufficient_privileges.jsp"));
				return;
			}
			try {
				int id = Integer.parseInt(req.getParameter("userId"));
				User usr = userDao.findById(id);
				if (usr != null) { // da nije neko u medjuvremno obrisao
					if (usr.getTablesConfigurations().size() ==0 && user.getMenus().size() == 0 && usr.getRestaurantMenagedBy() == null) {
						if(usr.getImage() != null){
							try{
								File deleteFile = new File(getServletContext().getRealPath("")+usr.getImage().getPath());
								// check if the file is present or not
								if (deleteFile.exists())
									deleteFile.delete();
							}catch(Exception ex){
								System.out.println(ex.getMessage());
							}
							imageDao.remove(usr.getImage());
						}
						
						
						userDao.remove(usr);
						System.out.println("Brisanje: " + id + " uspelo.");
						
						
						
						resp.sendRedirect(resp.encodeRedirectURL("./restaurantMenagers"));
					}else{
						System.out.println("Brisanje: " + id + " nije moguce jer ima restorane vezane za sebe!");
						resp.sendRedirect(resp.encodeRedirectURL("./restaurantMenagers"));
					}
				} else {
					System.out.println("Neko obrisao u medjuvremenu.");
					resp.sendRedirect(resp.encodeRedirectURL("./restaurantMenagers"));
				}
			} catch (Exception ex) {
				System.out.println("Brisanje nije uspelo.");
				resp.sendRedirect(resp.encodeRedirectURL("./restaurantMenagers"));
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
