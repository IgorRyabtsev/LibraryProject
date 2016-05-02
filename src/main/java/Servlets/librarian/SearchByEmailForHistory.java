package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Orders;
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
 * Created by igor on 02.05.16.
 */
@WebServlet(name = "SearchByEmailForHistory", urlPatterns = "/searchbyemailForHistory")
public class SearchByEmailForHistory extends HttpServlet {

    private final Logger logger = Logger.getLogger(SearchByEmailForHistory.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getParameter("email") == null) {
            logger.error("Troubles with email parameter");
            response.sendError(400);
            return;
        }

        String email = request.getParameter("email");
        if(email.isEmpty() || email.trim().isEmpty()) {
            logger.debug("Some empty fields");
            response.sendRedirect("/ordershistory");
            return;
        }

        List<Orders> ordersByEmail = Connections.getFactory().getOrdersDao().getOrdersByEmail(email);
        request.setAttribute("history",ordersByEmail);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/ordershistory.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
