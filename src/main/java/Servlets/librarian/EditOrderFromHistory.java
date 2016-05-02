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
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 02.05.16.
 */
@WebServlet(name = "EditOrderFromHistory", urlPatterns = "/editOrderFromHistory")
public class EditOrderFromHistory extends HttpServlet {

    private final Logger logger = Logger.getLogger(EditOrderFromHistory.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("id") == null) {
            logger.error("Parametres id is null!");
            response.sendError(400);
            return;
        }
        int idOrder;
        try {
            idOrder = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles wirh \"id\"",e);
            response.sendError(400);
            return;
        }

        Orders order = Connections.getFactory().getOrdersDao().getOrderById(idOrder);

        if(order.getReturn_date() == null) {
            String date = request.getParameter("release_date");
            java.sql.Date release_date;
            try {
                release_date = java.sql.Date.valueOf(date);
            } catch (IllegalArgumentException e) {
                logger.debug("Empty Date");
                String message = "wrongdate";
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


        String rel_date = request.getParameter("release_date");
        String ret_date = request.getParameter("return_date");
        java.sql.Date release_date;
        java.sql.Date return_date;
        try {
            release_date = java.sql.Date.valueOf(rel_date);
            return_date = java.sql.Date.valueOf(ret_date);
        } catch (IllegalArgumentException e) {
            logger.debug("Empty Date");
            String message = "wrongdate";
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
        return;

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null!");
            response.sendError(400);
            return;
        }

        int idOrder;
        try {
            idOrder = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Orders orderById = Connections.getFactory().getOrdersDao().getOrderById(idOrder);
        request.setAttribute("order",orderById);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/editOrderFromHistory.jsp").forward(request, response);
    }
}
