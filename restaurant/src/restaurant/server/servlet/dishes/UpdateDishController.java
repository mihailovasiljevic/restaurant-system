package restaurant.server.servlet.dishes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Dish;
import restaurant.server.entity.Menu;
import restaurant.server.entity.User;
import restaurant.server.session.DishDaoLocal;
import restaurant.server.session.MenuDaoLocal;

public class UpdateDishController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6411514538663745412L;
	@EJB
	private MenuDaoLocal menuDao;
	@EJB
	private DishDaoLocal dishDao;

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
			if (!(user.getUserType().getName()).equals("RESTAURANT_MENAGER")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			try {

				ObjectMapper resultMapper = new ObjectMapper();
				String dishName = "";
				String dishDescrp = "";
				Double dishPrice = -1.0;
				ObjectMapper mapper = new ObjectMapper();
				HashMap<String, String> data = mapper.readValue(req.getParameter("dishData"), HashMap.class);
				System.out.println("");
				for (String key : data.keySet()) {
					if (key.equals("dishName"))
						dishName = data.get(key);
					else if (key.equals("dishDescrp"))
						dishDescrp = data.get(key);
					else if (key.equals("dishPrice")) {
						try {
							dishPrice = Double.parseDouble(data.get(key));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							resp.setContentType("application/json; charset=utf-8");
							PrintWriter out = resp.getWriter();
							resultMapper.writeValue(out, "Cena nije u ispravnom formatu");
							e.printStackTrace();
							return;
						}
					}
				}

				if (dishName.equals("") || dishName == null || dishName.equals("")) {
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Ime jela ne sme ostati prazno");
					return;
				}
				Dish dish = (Dish) req.getSession().getAttribute("updateDish");
				if (dish != null) {
					dish.setDescription(dishDescrp);
					dish.setName(dishName);
					dish.setPrice(dishPrice);

					Dish persisted = dishDao.merge(dish);
					if (persisted == null) {
						resp.setContentType("application/json; charset=utf-8");
						PrintWriter out = resp.getWriter();
						resultMapper.writeValue(out, "Nije uspelo cuvanje jela.");
						return;
					}

					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "USPEH");
					return;
				}else{
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out,"Greska na sesiji. Molimo pokusajte ponovo od prvog koraka izmene.");
					return;
				}
			} catch (Exception ex) {

				ObjectMapper resultMapper = new ObjectMapper();
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, ex.getMessage());
				ex.printStackTrace();
				return;
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
