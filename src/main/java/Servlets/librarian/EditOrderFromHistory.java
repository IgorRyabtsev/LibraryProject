package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Orders;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 02.05.16.
 */

/**
 * Servlet for edit User Orders (given yet to reader books), change release/return date / delete orders
 * @author igor
 */
@WebServlet(name = "EditOrderFromHistory", urlPatterns = "/editOrderFromHistory")
public class EditOrderFromHistory extends HttpServlet {

    private final Logger logger = Logger.getLogger(EditOrderFromHistory.class);

    /**
     * Edit User Orders (given yet to reader books), change release/return date;
     * id - order id
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("id") == null) {
            logger.error("Parametres id is null!");
            response.sendError(400);
            return;
        }
        final int idOrder;
        try {
            idOrder = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles wirh \"id\"",e);
            response.sendError(400);
            return;
        }

        final Orders order = Connections.getFactory().getOrdersDao().getOrderById(idOrder);
        if(order.getReturn_date() == null) {
            final String date = request.getParameter("release_date");
            final java.sql.Date release_date;
            try {
                release_date = java.sql.Date.valueOf(date);
            } catch (IllegalArgumentException e) {
                logger.debug("Empty Date");
                final String message = "wrongdate";
                request.setAttribute("message",message);
                request.setAttribute("order",order);
                request.getRequestDispatcher("/WEB-INF/jsp/librarian/editOrderFromHistory.jsp").forward(request, response);
                return;
            }
            if ( !Connections.getFactory().getOrdersDao().setDate(idOrder,release_date,null) ){
                logger.error("Trouble in updating date");
            }
            response.sendRedirect("/ordershistory");
            return;
        }

        final String rel_date = request.getParameter("release_date");
        final String ret_date = request.getParameter("return_date");
        final Date release_date;
        final Date return_date;
        try {
            release_date = java.sql.Date.valueOf(rel_date);
            return_date = java.sql.Date.valueOf(ret_date);
        } catch (IllegalArgumentException e) {
            logger.debug("Empty Date");
            final String message = "wrongdate";
            request.setAttribute("message",message);
            request.setAttribute("order",order);
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/editOrderFromHistory.jsp").forward(request, response);
            return;
        }

        if (release_date.getTime() > return_date.getTime()) {
            logger.debug("Not correct date.");
            String message = "releasedatebigger";
            request.setAttribute("message",message);
            request.setAttribute("order",order);
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/editOrderFromHistory.jsp").forward(request, response);
            return;
        }

        if ( !Connections.getFactory().getOrdersDao().setDate(idOrder,release_date,return_date) ){
            logger.error("Trouble in updating date");
        }
        response.sendRedirect("/ordershistory");
    }

    /**
     * Get order id and forward to editting order Form editOrderFromHistory.jsp
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

        final int idOrder;
        try {
            idOrder = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        final Orders orderById = Connections.getFactory().getOrdersDao().getOrderById(idOrder);
        request.setAttribute("order",orderById);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/editOrderFromHistory.jsp").forward(request, response);
    }
}
