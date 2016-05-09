package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Reader;
import org.apache.log4j.Logger;

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

/**
 * Servlet for make librarian user
 * @author igor
 */

@WebServlet(name = "AddLibrarianForm", urlPatterns = "/addlibrarianform")
public class AddLibrarianForm extends HttpServlet {

    private final Logger logger = Logger.getLogger(AddLibrarianForm.class);

    /**
     * Create reader to librarian
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null!");
            response.sendError(400);
            return;
        }

        final int idReader;
        try {
            idReader = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        if (!Connections.getFactory().getReaderDao().makeLibrarian(idReader)) {
            logger.error("Troubles with making librarian!");
        }
        response.sendRedirect("/addlibrarian");
    }

    /**
     * Get information about Reader and forward it to make librarian Form: addlibrarianForm.jsp
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null!");
            response.sendError(400);
            return;
        }

        final int idReader;
        try {
            idReader = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        final Reader reader = Connections.getFactory().getReaderDao().getReaderById(idReader);
        request.setAttribute("reader", reader);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/addlibrarianForm.jsp").forward(request, response);
    }
}
