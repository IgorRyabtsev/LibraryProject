package main.java.Servlets;

import main.java.Servlets.security.EncodingPassword;
import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Reader;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            doGet(request, response);
            System.out.println("some empty boxes");
            return;
        }

        Reader readerByEmail = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if(readerByEmail == null) {
            doGet(request, response);
            System.out.println("not existed email");
            return;
        }

//        System.out.println(readerByEmail);

        if(readerByEmail.getPassword().equals(EncodingPassword.hash(password))) {
            HttpSession session = request.getSession();
            session.setAttribute("user_session",readerByEmail);
            request.getRequestDispatcher("/jsp/main.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
        System.out.println("not correct password");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }
}
