package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 28.04.16.
 */
@WebServlet(name = "SearchByEmailTakeLibrarian",urlPatterns = "/searchbyemailAddLibrarian")
public class SearchByEmailTakeLibrarian extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if(email.isEmpty() || email.trim().isEmpty()) {
            List<Reader> allReaders = Connections.getFactory().getReaderDao().getAll();
            request.setAttribute("allReaders",allReaders);
            request.getRequestDispatcher("/jsp/librarian/listofreadersLibrarian.jsp").forward(request, response);
            return;
        }
        List<Reader> allReaders = new ArrayList<>();
        Reader readerByEmail = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if (readerByEmail!=null) {
            allReaders.add(readerByEmail);
        }
        request.setAttribute("allReaders",allReaders);
        request.getRequestDispatcher("/jsp/librarian/listofreadersLibrarian.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
