package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Orders;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by igor on 27.04.16.
 */

/**
 * Servlet gives all orders
 * @author igor
 */
@WebServlet(name = "OrdersHistory", urlPatterns = "/ordershistory")
public class OrdersHistory extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Get all orders and forwart to ordershistory.jsp
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Orders> ordersByEmail = Connections.getFactory().getOrdersDao().getAllOrders();
        request.setAttribute("history",ordersByEmail);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/ordershistory.jsp").forward(request, response);
    }
}
