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
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by igor on 25.04.16.
 */

@WebServlet(name = "LogIn", urlPatterns = "/login")
public class LogIn extends HttpServlet {

    private final Logger logger = Logger.getLogger(LogIn.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            logger.debug("Some empty fields");
            request.setAttribute("messageSignIn","empty");
            doGet(request, response);
            return;
        }

        Reader readerByEmail = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if(readerByEmail == null) {
            logger.debug("No user with such email");
            request.setAttribute("messageSignIn","nosuchuser");
            doGet(request, response);
            return;
        }

        if(readerByEmail.getPassword().equals(EncodingPassword.hash(password))) {
            HttpSession session = request.getSession();
            session.setAttribute("user_session",readerByEmail);
            request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
            return;
        }
        logger.debug("Wrong password");
        request.setAttribute("messageSignIn","notcorrectpassword");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
