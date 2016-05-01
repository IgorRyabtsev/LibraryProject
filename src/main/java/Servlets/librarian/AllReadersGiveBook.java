package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by igor on 28.04.16.
 */
@WebServlet(name = "AllReadersGiveBook", urlPatterns = "/allreadersForGivebook")
public class AllReadersGiveBook extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Reader> allReaders = Connections.getFactory().getReaderDao().getAll();
        request.setAttribute("allReaders",allReaders);
//        request.getRequestDispatcher("/jsp/librarian/allreadersForGivebook.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/allreadersForGivebook.jsp").forward(request, response);
    }
}
