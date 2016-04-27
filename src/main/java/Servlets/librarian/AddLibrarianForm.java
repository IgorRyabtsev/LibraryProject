package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 27.04.16.
 */
@WebServlet(name = "AddLibrarianForm", urlPatterns = "/addlibrarianform")
public class AddLibrarianForm extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendError(400);
            return;
        }

        int idReader;
        try {
            idReader = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Connections.getFactory().getReaderDao().makeLibrarian(idReader);
        List<Reader> allReaders = Connections.getFactory().getReaderDao().getAll();
        request.setAttribute("allReaders",allReaders);
        request.getRequestDispatcher("/jsp/librarian/listofreadersLibrarian.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendError(400);
            return;
        }

        int idReader;
        try {
            idReader = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        Reader reader = Connections.getFactory().getReaderDao().getReaderById(idReader);
        request.setAttribute("reader", reader);
        request.getRequestDispatcher("/jsp/librarian/addlibrarianForm.jsp").forward(request, response);
    }
}
