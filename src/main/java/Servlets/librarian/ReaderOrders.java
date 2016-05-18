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
 * Created by igor on 28.04.16.
 */

/**
 * Servlet for taking list of instances, that reader want to take
 * @author igor
 */
@WebServlet(name = "ReaderOrders", urlPatterns = "/readerorders")
public class ReaderOrders extends HttpServlet {

    private final Logger logger = Logger.getLogger(ReaderOrders.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Get list of instances, that reader want to take, info from table OrdNum
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
            logger.error("Troubles with parameter \"id\"!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        final Reader readerById = Connections.getFactory().getReaderDao().getReaderById(idReader);
        if (readerById==null) {
            logger.error("Troubles with idReader");
            response.sendError(400);
            return;
        }
        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReaderForLibrarian(readerById);
        request.setAttribute("instances",instancesByReader);
        request.setAttribute("readers",readerById);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/userorders.jsp").forward(request, response);
    }
}
