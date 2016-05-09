package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;
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
 * Created by igor on 27.04.16.
 */

/**
 * Servlet for deletion instance
 * @author igor
 */
@WebServlet(name = "DeleteInstance", urlPatterns = "/deleteinstance")
public class DeleteInstance extends HttpServlet {

    private final Logger logger = Logger.getLogger(DeleteInstance.class);

    /**
     * Delete instance by id instance from parameter
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null");
            response.sendError(400);
            return;
        }
        final int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter \"id\".");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        if ( !Connections.getFactory().getInstanceDao().deleteInstanceById(instanceId)) {
            logger.error("Troubles with delete reader");
        }
        response.sendRedirect("/avaliablebooks");
    }

    /**
     * Get information about instance and forward to delete instance form: deleteinstance.jsp
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null");
            response.sendError(400);
            return;
        }
        final int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter \"id\".");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        request.setAttribute("instAuth", instanceAuthorList);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/deleteinstance.jsp").forward(request, response);
    }
}
