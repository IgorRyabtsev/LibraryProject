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
 * Created by igor on 28.04.16.
 */

/**
 * Servlet for search user, for give him book
 */
@WebServlet(name = "SearchByEmailForGiveBook", urlPatterns = "/searchbyemailForGiveBook")
public class SearchByEmailForGiveBook extends HttpServlet {

    private final Logger logger = Logger.getLogger(SearchByEmailForGiveBook.class);

    /**
     * Get user by email for giving him book
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("email") == null) {
            logger.error("Troubles with email parameter");
            response.sendError(400);
            return;
        }

        final String email = request.getParameter("email");
        if(email.isEmpty() || email.trim().isEmpty()) {
            logger.debug("Some empty fields");
            response.sendRedirect("/allreadersForGivebook");
            return;
        }
        List<Reader> allReaders = new ArrayList<>();
        final Reader readerByEmail = Connections.getFactory().getReaderDao().getReaderByEmail(email);
        if (readerByEmail!=null) {
            allReaders.add(readerByEmail);
        }
        request.setAttribute("allReaders",allReaders);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/allreadersForGivebook.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
