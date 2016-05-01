package main.java.Servlets;

import main.java.Servlets.security.EncodingPassword;
import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Reader;

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

    Pattern p = Pattern.compile("[0-9a-z_]+@[0-9a-z_^\\.]+\\.[a-z]{2,3}");
    Matcher m;
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
            request.setAttribute("messageSignUp","empty");
            doGet(request, response);
            return;
        }

        //wrong year format
        Integer yearOfBirth;
        try {
            yearOfBirth = new Integer(year);
        }catch (NumberFormatException e) {
            request.setAttribute("messageSignUp","wrongyear");
            doGet(request, response);
            return;
        }

        //year out of range
        if( yearOfBirth > getCurrentYear() || yearOfBirth < getCurrentYear()-150){
            request.setAttribute("messageSignUp","wrongyear");
            doGet(request, response);
            return;
        }

        Matcher m = p.matcher(email);
        if( !m.matches() ){
            request.setAttribute("messageSignUp","wrongEmail");
            doGet(request, response);
            return;
        }
        //such email is already exist
        Reader reader = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if(reader != null) {
            request.setAttribute("messageSignUp","emailExists");
            doGet(request, response);
            return;
        }

        Reader newReader = new Reader(0,namef,names,namep,yearOfBirth,email,EncodingPassword.hash(password),"user");
        if (Connections.getFactory().getReaderDao().insertReader(newReader) == false) {
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