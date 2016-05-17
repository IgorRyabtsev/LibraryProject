package main.java.Servlets;

import main.java.Servlets.security.EncodingPassword;
import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Reader;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by igor on 25.04.16.
 */

/**
 * Servlet for registration
 * @author igor
 */
@WebServlet(name = "registration", urlPatterns = "/signup")
public class Registration extends HttpServlet {
    private final Logger logger = Logger.getLogger(Registration.class);
    private final Pattern p = Pattern.compile("[0-9a-z_]+@[0-9a-z_^\\.]+\\.[a-z]{2,3}");
    private Matcher m;

    /**
     * Registration new user
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("namef") == null || request.getParameter("names") == null ||
                request.getParameter("namep") == null || request.getParameter("year") == null ||
                request.getParameter("email") == null || request.getParameter("password") == null) {
            logger.error("Troubles with parameters");
            response.sendError(400);
            return;
        }

        final String namef = request.getParameter("namef");
        final String names = request.getParameter("names");
        final String namep = request.getParameter("namep");
        final String year = request.getParameter("year");
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");

        //some empty values
        if(namef.trim().isEmpty() || names.trim().isEmpty() || namep.trim().isEmpty() ||
                email.trim().isEmpty() || password.trim().isEmpty() || year.trim().isEmpty()) {
            logger.debug("Some empty dields");
            request.setAttribute("messageSignUp","empty");
            doGet(request, response);
            return;
        }

        //too big size
        if(namef.length()>100 || names.length()>100 || namep.length()>100 ||
                email.length()>100 ) {
            logger.debug("Too long value");
            request.setAttribute("messageSignUp","tooLong");
            doGet(request, response);
            return;
        }

        //wrong year format
        final Integer yearOfBirth;
        try {
            yearOfBirth = new Integer(year);
        }catch (NumberFormatException e) {
            logger.debug("Wrong year format");
            request.setAttribute("messageSignUp","wrongyear");
            doGet(request, response);
            return;
        }

        //year out of range
        if( yearOfBirth > getCurrentYear() || yearOfBirth < getCurrentYear()-150){
            logger.debug("Wrong year");
            request.setAttribute("messageSignUp","wrongyear");
            doGet(request, response);
            return;
        }

        m = p.matcher(email);
        if( !m.matches() ){
            logger.debug("Wrong email format");
            request.setAttribute("messageSignUp","wrongEmail");
            doGet(request, response);
            return;
        }
        //such email is already exist
        final Reader reader = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if(reader != null) {
            logger.debug("Wrong such email is already exists");
            request.setAttribute("messageSignUp","emailExists");
            doGet(request, response);
            return;
        }

        final Reader newReader = new Reader(0,namef,names,namep,yearOfBirth,email,EncodingPassword.hash(password),"user");
        if (Connections.getFactory().getReaderDao().insertReader(newReader) == false) {
            logger.error("Trouble with insertion new Reader");
            response.sendError(400);
            return;
        }
        response.sendRedirect("/login");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    /**
     * Get current year
     * @return current year
     */
    private static int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }

}