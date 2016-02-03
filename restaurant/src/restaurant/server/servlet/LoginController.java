package restaurant.server.servlet;

import org.apache.openjpa.persistence.NoResultException;
import restaurant.externals.HashPassword;
import restaurant.server.entity.User;
import restaurant.server.session.UserDaoLocal;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class LoginController extends HttpServlet {

    @EJB private UserDaoLocal userDao;

    private static Logger log = Logger.getLogger(LoginController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        try{
            String userEmail = req.getParameter("userEmail");
            String userPassword = req.getParameter("userPassword");

            if ((userEmail == null) || (userEmail.equals("")) || (userPassword == null) || (userPassword.equals(""))) {
                resp.sendRedirect(resp.encodeRedirectURL("./login.jsp"));
                return;
            }

            checkUser(userEmail, userPassword, req,resp);


        }catch (EJBException e){
            if (e.getCause().getClass().equals(NoResultException.class))
                resp.sendRedirect(resp.encodeRedirectURL("./login_error.jsp"));
            else {
                throw e;
            }
        }
    }

    private void checkUser(String userEmail, String userPassword, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userDao.findUserByEmail(userEmail);
        if(user != null){
            byte[] salt = user.getSalt();
            byte[] password = user.getPassword();
            HashPassword hshPass = new HashPassword();

            char[] charInsertedPass = new char[userPassword.length()];

            boolean success = hshPass.isPassword(charInsertedPass, salt, password);

            if(success) {
                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
                log.info("User " + user.getEmail() + " has logged in." + timeStamp);
                getServletContext().getRequestDispatcher("/ReadController").forward(req, resp);
                return;
            }
            else {
                resp.sendRedirect(resp.encodeRedirectURL("./login_error.jsp"));
                return;
            }

        }else{
            resp.sendRedirect(resp.encodeRedirectURL("./login_error.jsp"));
            return;
        }
    }
}
