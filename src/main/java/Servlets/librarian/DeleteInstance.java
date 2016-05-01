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
@WebServlet(name = "DeleteInstance", urlPatterns = "/deleteinstance")
public class DeleteInstance extends HttpServlet {

    private final Logger logger = Logger.getLogger(DeleteInstance.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null");
            response.sendError(400);
            return;
        }
        int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter \"id\".");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Connections.getFactory().getInstanceDao().deleteInstanceById(instanceId);
        response.sendRedirect("/avaliablebooks");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null");
            response.sendError(400);
            return;
        }
        int instanceId;
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
