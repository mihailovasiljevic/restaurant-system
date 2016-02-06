package restaurant.server.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import restaurant.server.entity.Vozilo;
import restaurant.server.session.GorivoDaoLocal;
import restaurant.server.session.MenjacDaoLocal;
import restaurant.server.session.VoziloDaoLocal;

public class CreateController extends HttpServlet {

	private static final long serialVersionUID = -2544396238785425302L;
	
	private static Logger log = Logger.getLogger(CreateController.class);

	@EJB
	private VoziloDaoLocal voziloDao;

	@EJB
	private GorivoDaoLocal gorivoDao;

	@EJB
	private MenjacDaoLocal menjacDao;
	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String naziv = null;
			Long predjeniPut = null;
			Integer snaga = null;
			Integer godinaProizvodnje = null;
			Integer cena = null;
			Integer radnaZapremina = null;
			Integer brojVrata = null;
			Integer brojSedista = null;
			Integer brojBrzina = null;
			Integer masa = null;
			Boolean klimaUredjaj = null;
			Date datumPostavljanja = null;
			Integer gorivoId = null;
			Integer menjacId = null;

			if ((request.getSession().getAttribute("admin")) == null) {
				response.sendRedirect(response.encodeURL("./login.jsp"));
				return;
			}

			if ((request.getParameter("naziv") != null) && (!"".equals(request.getParameter("naziv")))) {
				naziv = request.getParameter("naziv");
			}

			if ((request.getParameter("predjeniPut") != null) && (!"".equals(request.getParameter("predjeniPut")))) {
				predjeniPut = new Long(request.getParameter("predjeniPut"));
			}

			if ((request.getParameter("cena") != null) 	&& (!"".equals(request.getParameter("cena")))) {
				cena = new Integer(request.getParameter("cena"));
			}

			if ((request.getParameter("snaga") != null) && (!"".equals(request.getParameter("snaga")))) {
				snaga = new Integer(request.getParameter("snaga"));
			}

			if ((request.getParameter("godinaProizvodnje") != null) && (!"".equals(request.getParameter("godinaProizvodnje")))) {
				godinaProizvodnje = new Integer(request.getParameter("godinaProizvodnje"));
			}

			if ((request.getParameter("radnaZapremina") != null) && (!"".equals(request.getParameter("radnaZapremina")))) {
				radnaZapremina = new Integer(request.getParameter("radnaZapremina"));
			}

			if ((request.getParameter("brojVrata") != null) && (!"".equals(request.getParameter("brojVrata")))) {
				brojVrata = new Integer(request.getParameter("brojVrata"));
			}

			if ((request.getParameter("brojSedista") != null) && (!"".equals(request.getParameter("brojSedista")))) {
				brojSedista = new Integer(request.getParameter("brojSedista"));
			}

			if ((request.getParameter("brojBrzina") != null) && (!"".equals(request.getParameter("brojBrzina")))) {
				brojBrzina = new Integer(request.getParameter("brojBrzina"));
			}

			if ((request.getParameter("masa") != null)	&& (!"".equals(request.getParameter("masa")))) {
				masa = new Integer(request.getParameter("masa"));
			}

			if ((request.getParameter("klimaUredjaj") != null) 	&& (!"".equals(request.getParameter("klimaUredjaj")))) {
				klimaUredjaj = new Boolean(request.getParameter("klimaUredjaj"));
			}

			if ((request.getParameter("datumPostavljanja") != null) && (!"".equals(request.getParameter("datumPostavljanja")))) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				datumPostavljanja = sdf.parse(request.getParameter("datumPostavljanja"));
			}

			if ((request.getParameter("gorivo") != null) && (!"".equals(request.getParameter("gorivo")))) {
				gorivoId = new Integer(request.getParameter("gorivo"));
			}

			if ((request.getParameter("menjac") != null) && (!"".equals(request.getParameter("menjac")))) {
				menjacId = new Integer(request.getParameter("menjac"));
			}

			Vozilo vozilo = new Vozilo();

			if (naziv != null)
				vozilo.setNazivVozila(naziv);

			if (predjeniPut != null)
				vozilo.setPredjeniPutVozila(predjeniPut);

			if (snaga != null)
				vozilo.setSnagaVozila(snaga);

			if (godinaProizvodnje != null)
				vozilo.setGodinaProizvodnjeVozila(godinaProizvodnje);

			if (cena != null)
				vozilo.setCenaVozila(cena);

			if (radnaZapremina != null)
				vozilo.setRadnaZapreminaVozila(radnaZapremina);

			if (brojVrata != null)
				vozilo.setBrojVrataVozila(brojVrata);

			if (brojSedista != null)
				vozilo.setBrojSedistaVozila(brojSedista);

			if (brojBrzina != null)
				vozilo.setBrojBrzinaVozila(brojBrzina);

			if (masa != null)
				vozilo.setMasaVozila(masa);

			if (klimaUredjaj != null)
				vozilo.setVoziloImaKlimaUredjaj(true);
			else
				vozilo.setVoziloImaKlimaUredjaj(false);

			if (datumPostavljanja != null)
				vozilo.setDatumPostavljanjaVozila(datumPostavljanja);

			if (gorivoId != null)
				vozilo.setGorivo(gorivoDao.findById(gorivoId));

			if (menjacId != null)
				vozilo.setMenjac(menjacDao.findById(menjacId));
			
			voziloDao.persist(vozilo);

			getServletContext().getRequestDispatcher("/ReadController").forward(request, response);
			return;
			
		} catch (ServletException e) {
			log.error(e);
			throw e;
		} catch (IOException e) {
			log.error(e);
			throw e;
		} catch (ParseException e) {
			log.error(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
