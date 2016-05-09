package main.java.Servlets;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
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
 * Created by igor on 26.04.16.
 */

/**
 * Servlet for searching book
 * @author igor
 */
@WebServlet(name = "SearchBook", urlPatterns = "/searchbook")
public class SearchBook extends HttpServlet {

    private final static Logger logger = Logger.getLogger(SearchBook.class);

    /**
     * Search book by conditions
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("namef") == null || request.getParameter("names") == null ||
                request.getParameter("namep") == null || request.getParameter("bookname") == null) {
            logger.error("Troubles with parameters");
            response.sendError(400);
            return;
        }

        final String namef = request.getParameter("namef");
        final String names = request.getParameter("names");
        final String namep = request.getParameter("namep");
        final String bookname = request.getParameter("bookname");
        final Author author = new Author(0,namef,names,namep,0);
        final Book book = new Book(0,bookname);

        List<Map<Instance, List<Author>>> allInstances = Connections.getFactory().getInstanceDao().getInstanceByCondition(author,book);
        request.setAttribute("instances",allInstances);
        request.getRequestDispatcher("/WEB-INF/jsp/avaliablebooks.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
