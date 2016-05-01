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
@WebServlet(name = "registration", urlPatterns = "/signup")
public class Registration extends HttpServlet {
    private final Logger logger = Logger.getLogger(Registration.class);
    private final Pattern p = Pattern.compile("[0-9a-z_]+@[0-9a-z_^\\.]+\\.[a-z]{2,3}");
    private Matcher m;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namef = request.getParameter("namef");
        String names = request.getParameter("names");
        String namep = request.getParameter("namep");
        String year = request.getParameter("year");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //some empty values
        if(namef.trim().isEmpty() || names.trim().isEmpty() || namep.trim().isEmpty() ||
                email.trim().isEmpty() || password.trim().isEmpty() || year.trim().isEmpty()) {
            logger.debug("Some empty dields");
            request.setAttribute("messageSignUp","empty");
            doGet(request, response);
            return;
        }

        //wrong year format
        Integer yearOfBirth;
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
        Reader reader = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if(reader != null) {
            logger.debug("Wrong such email is already exists");
            request.setAttribute("messageSignUp","emailExists");
            doGet(request, response);
            return;
        }

        Reader newReader = new Reader(0,namef,names,namep,yearOfBirth,email,EncodingPassword.hash(password),"user");
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

    private static int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }

}