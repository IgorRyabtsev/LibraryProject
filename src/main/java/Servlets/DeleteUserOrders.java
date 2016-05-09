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
 * Servlet for deletion order from table OrdNum
 * @author igor
 */
@WebServlet(name = "DeleteUserOrders", urlPatterns = "/deleteuserorder")
public class DeleteUserOrders extends HttpServlet {

    private final Logger logger = Logger.getLogger(DeleteUserOrders.class);

    /**
     * Delete user order from OrdNum table
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
        if (request.getParameter("publish") == null) {
            logger.error("publish parameter is null!");
            response.sendError(400);
            return;
        }


        final int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        final String publish = request.getParameter("publish");
        Map.Entry<Instance, List<Author>> instanceById = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        final Reader reader = (Reader) request.getSession().getAttribute("user_session");

        Connections.getFactory().getReaderOrdersDao().deleteOrderByReader(reader,instanceById.getKey().getBook(),publish);

        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReader(reader);
        if(instancesByReader == null) {
            logger.error("Troubles with instances by Reader!");
            response.sendError(400);
            return;
        }
        response.sendRedirect("/userorders");
    }

    /**
     * Forward to delete order form
     * @param request request
     * @param response responce
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
            logger.error("Troubles with id!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        final Reader reader = (Reader) request.getSession().getAttribute("user_session");
        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReader(reader);
        if(instancesByReader == null) {
            logger.error("Troubles with instances by Reader!");
            response.sendError(400);
            return;
        }

        Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        request.setAttribute("instAuth", instanceAuthorList);
        request.getRequestDispatcher("/WEB-INF/jsp/deleteuserorders.jsp").forward(request, response);
    }
}