package main.java.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by igor on 26.04.16.
 */

/**
 * Servlet for logout
 * @author igor
 */
@WebServlet(name = "SignOut", urlPatterns = "/signout")
public class SignOut extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Logout, invalidate session
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.logout();
        final HttpSession session = request.getSession(false);
        if (session!= null){
            session.invalidate();
        }
        request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
    }
}
