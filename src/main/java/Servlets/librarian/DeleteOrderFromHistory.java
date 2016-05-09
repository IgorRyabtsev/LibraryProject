package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by igor on 02.05.16.
 */

/**
 * Servlet for delete user order from history
 * @author igor
 */
@WebServlet(name = "DeleteOrderFromHistory", urlPatterns = "/deleteOrderFromHistory")
public class DeleteOrderFromHistory extends HttpServlet {

    private final Logger logger = Logger.getLogger(DeleteOrderFromHistory.class);

    /**
     * Delete user order from history (by id order)
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

        final int idOrder;
        try {
            idOrder = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        if ( !Connections.getFactory().getOrdersDao().deleteById(idOrder)) {
            logger.error("Troubles with deleting from Orders!");
        }
        response.sendRedirect("/ordershistory");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
