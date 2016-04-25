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

/**
 * Created by igor on 25.04.16.
 */
@WebServlet(name = "registration", urlPatterns = "/signup")
public class Registration extends HttpServlet {

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
            doGet(request, response);
            System.out.println("empty");
            return;
        }

        //wrong year format
        Integer yearOfBirth;
        try {
            yearOfBirth = new Integer(year);
        }catch (NumberFormatException e) {
            doGet(request, response);
            System.out.println("year format");
            return;
        }

        //year out of range
        if( yearOfBirth > getCurrentYear() || yearOfBirth < getCurrentYear()-150){
            doGet(request, response);
            System.out.println("year");
            return;
        }

        //such emil is already exist
        Reader reader = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if(reader != null) {
            doGet(request, response);
            System.out.println("email is already exist");
            return;

        }

        Reader newReader = new Reader(0,namef,names,namep,yearOfBirth,email,EncodingPassword.hash(password),"user");
        if (Connections.getFactory().getReaderDao().insertReader(newReader) == false) {
            response.sendError(400);
            return;
        }
        response.sendRedirect("/jsp/login.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/signup.jsp").forward(request, response);
    }

    private static int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }

}