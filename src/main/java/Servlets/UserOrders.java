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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 26.04.16.
 */

/**
 * Class to take all order's instances of user
 * @author igor
 */
@WebServlet(name = "UserOrders", urlPatterns = "/userorders")
public class UserOrders extends HttpServlet {
    private static Logger logger = Logger.getLogger(UserOrders.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Get all order's instances and forward to userorders.jsp"
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
        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReaderForLibrarian(reader);
        request.setAttribute("instances",instancesByReader);
        request.getRequestDispatcher("/WEB-INF/jsp/userorders.jsp").forward(request, response);
    }
}
