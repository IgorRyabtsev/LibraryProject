package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Reader;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 03.05.16.
 */
@WebServlet(name = "SearchByEmailForDeleteReader", urlPatterns = "/searchbyemailForDeleteReader")
public class SearchByEmailForDeleteReader extends HttpServlet {

    private final Logger logger = Logger.getLogger(SearchByEmailForDeleteReader.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if(email.isEmpty() || email.trim().isEmpty()) {
            logger.debug("Some empty fields");
            response.sendRedirect("/deleteReader");
            return;
        }
        List<Reader> allReaders = new ArrayList<>();
        Reader readerByEmail = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if (readerByEmail!=null) {
            allReaders.add(readerByEmail);
        }
        request.setAttribute("allReaders",allReaders);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/deleteReaderList.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
