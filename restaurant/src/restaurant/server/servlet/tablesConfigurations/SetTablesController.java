package restaurant.server.servlet.tablesConfigurations;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantTable;
import restaurant.server.entity.TablesConfiguration;
import restaurant.server.entity.User;
import restaurant.server.session.RestaurantTableDaoLocal;
import restaurant.server.session.TablesConfigurationDaoLocal;

public class SetTablesController extends HttpServlet{
	
	@EJB
	private TablesConfigurationDaoLocal confDao;
	@EJB
	private RestaurantTableDaoLocal tableDao;

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
			if ((user.getUserType().getName()).equals("GUEST")) {
				req.getSession().setAttribute("infoMessage", "Nemate ovlascenja da pristupite stranici!");
				resp.sendRedirect(resp.encodeRedirectURL("../../index.jsp"));
				return;
			}
			ObjectMapper resultMapper = new ObjectMapper();
			ObjectMapper mapper = new ObjectMapper();

			TablesConfiguration conf = (TablesConfiguration) req.getSession().getAttribute("tablesConfiguration");
			if (conf == null) {
				resp.setContentType("application/json; charset=utf-8");
				PrintWriter out = resp.getWriter();
				resultMapper.writeValue(out, "Greska na sesiji. Pokusajte ponovo od prvog koraka konfiguracije.");
				return;
			}

			HashMap<String, String> data = mapper.readValue(req.getParameter("tablesData"), HashMap.class);
			String checkedValues = "";
			int reservationForHowLong = -1;
			ArrayList<String> rowsCols = new ArrayList<>();
			System.out.println("");
			for (String key : data.keySet()) {
				if (key.equals("checkedValues")) {
					if (!data.get(key).equals(" ")) {
						String array = data.get(key).replaceAll("\\s+", "");
						String[] values = array.substring(0, array.length() - 1).split(";");
						for (String s : values) {
							if (!s.equals("null")) {
								rowsCols.add(s);
							}
						}
					}
				}
			}
			

			for(String s : rowsCols){
				String[] values = s.split(",");
				int row = Integer.parseInt(values[0]);
				int col = Integer.parseInt(values[1]);
				RestaurantTable table = new RestaurantTable();
				table.setCol(col);
				table.setRow(row);
				table.setName("table_"+conf.getId()+"_"+row+col);
				table.setTablesConfiguration(conf);
				table.setReserved(false);
				RestaurantTable persTable = tableDao.persist(table);
				if(persTable == null){
					Iterator<RestaurantTable> it = conf.getTables().iterator();
					while(it.hasNext()){
						RestaurantTable tbl = it.next();
						tableDao.remove(tbl);
					}
					confDao.remove(conf);
					
					resp.setContentType("application/json; charset=utf-8");
					PrintWriter out = resp.getWriter();
					resultMapper.writeValue(out, "Nije uspelo cuvanje stola.");
					return;								
				}
				
			}
			resp.setContentType("application/json; charset=utf-8");
			PrintWriter out = resp.getWriter();
			resultMapper.writeValue(out, "USPEH.");
			return;			
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
}
