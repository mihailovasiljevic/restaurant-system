package restaurant.server.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import restaurant.server.session.GorivoDaoLocal;
import restaurant.server.session.MenjacDaoLocal;
import restaurant.server.session.VoziloDaoLocal;

public class PrepareUpdateController extends HttpServlet {

	private static final long serialVersionUID = 1069341894540010096L;
	
	private static Logger log = Logger.getLogger(PrepareUpdateController.class);

	@EJB
	private VoziloDaoLocal voziloDao;

	@EJB
	private MenjacDaoLocal menjacDao;

	@EJB
	private GorivoDaoLocal gorivoDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			if ((request.getSession().getAttribute("admin")) == null) {
				response.sendRedirect(response.encodeURL("./login.jsp"));
				return;
			}

			request.setAttribute("menjaci", menjacDao.findAll());
			request.setAttribute("goriva", gorivoDao.findAll());

			String voziloId = request.getParameter("voziloId");

			if ((voziloId != null) && (!voziloId.equals(""))) {
				request.setAttribute("vozilo", voziloDao.findById(Integer.parseInt(voziloId)));
				getServletContext().getRequestDispatcher("/update.jsp").forward(request, response);
			}
			
		} catch (ServletException e) {
			log.error(e);
			throw e;
		} catch (IOException e) {
			log.error(e);
			throw e;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}