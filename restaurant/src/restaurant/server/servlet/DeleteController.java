package restaurant.server.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import restaurant.server.entity.Vozilo;
import restaurant.server.session.VoziloDaoLocal;

public class DeleteController extends HttpServlet {

	private static final long serialVersionUID = -6495686002772669396L;
	
	private static Logger log = Logger.getLogger(DeleteController.class);
	
	@EJB
	private VoziloDaoLocal voziloDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			if ((request.getSession().getAttribute("admin")) == null) {
				response.sendRedirect(response.encodeURL("./login.jsp"));
				return;
			}

			String voziloId = null;
			voziloId = request.getParameter("voziloId");

			Vozilo vozilo = voziloDao.findById(Integer.parseInt(voziloId));
			voziloDao.remove(vozilo);

			getServletContext().getRequestDispatcher("/ReadController").forward(request, response);
			
		} catch (ServletException e) {
			log.error(e);
			throw e;
		} catch (IOException e) {
			log.error(e);
			throw e;
		}
	}

	protected void doPost(HttpServletRequest request, 	HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
