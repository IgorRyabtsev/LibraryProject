package main.java.Servlets;

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
import java.util.List;

/**
 * Created by igor on 26.04.16.
 */

/**
 * Class to take user history in library
 * @author igor
 */
@WebServlet(name = "UserHistory", urlPatterns = "/userhistory")
public class UserHistory extends HttpServlet {
    private static Logger logger = Logger.getLogger(UserHistory.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Get all user's history from Order's table
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final Reader reader = (Reader) request.getSession().getAttribute("user_session");
        if(reader==null) {
            logger.error("Troubles with user_session");
            response.sendError(400);
            return;
        }
        List<Orders> ordersByEmail = Connections.getFactory().getOrdersDao().getOrdersByEmail(reader.getEmail(),false);
        request.setAttribute("history",ordersByEmail);
        request.getRequestDispatcher("/WEB-INF/jsp/userhistory.jsp").forward(request, response);
    }
}
