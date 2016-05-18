package main.java.Servlets;

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
 * Created by igor on 26.04.16.
 */

/**
 * Servlet for ordering book by Reader
 * @author igor
 */
@WebServlet(name = "OrderBook",urlPatterns = "/orderbook")
public class OrderBook extends HttpServlet {
    private final Logger logger = Logger.getLogger(OrderBook.class);

    /**
     * Add order for user into OrdNum table
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("id") == null) {
            logger.error("id is null!");
            response.sendError(400);
            return;
        }

        final int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Trouble with id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        final Reader reader = (Reader) request.getSession().getAttribute("user_session");
        if (reader == null) {
            logger.error("user_session troubles!");
            response.sendError(400);
            return;
        }

        Map.Entry<Instance,List<Author>> instance =  Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        if (instance == null) {
            logger.error("Troubles with Instances, no Instances with such id, that in parameter");
            response.sendError(400);
            return;
        }

        if (!Connections.getFactory().getReaderOrdersDao().insertOrder(reader,instance.getKey())) {
            logger.error("Troubles with insertion new order");
            response.sendError(400);
            return;
        }
        response.sendRedirect("/avaliablebooks");
    }

    /**
     * Get information about current instance with such id
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("id is null!");
            response.sendError(400);
            return;
        }
        final int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Trouble with id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        request.setAttribute("instAuth", instanceAuthorList);
        request.getRequestDispatcher("/WEB-INF/jsp/orderbook.jsp").forward(request, response);
    }
}
