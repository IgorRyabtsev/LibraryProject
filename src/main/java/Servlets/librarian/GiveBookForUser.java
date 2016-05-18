package main.java.Servlets.librarian;

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
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 28.04.16.
 */

/**
 * Servlet for give book to reader
 * @author igor
 */
@WebServlet(name = "GiveBookForUser", urlPatterns = "/givebook")
public class GiveBookForUser extends HttpServlet {

    private final Logger logger = Logger.getLogger(GiveBookForUser.class);

    /**
     * Give book to reader. id_i -instance id; id_r -reader id; date-release date
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id_i") == null || request.getParameter("id_r") == null || request.getParameter("date") == null) {
            logger.error("Parametres id_i or id_r or date is null!");
            response.sendError(400);
            return;
        }
        final int instanceId;
        final int readerId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id_i"));
            readerId = Integer.valueOf(request.getParameter("id_r"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameters id_i or id_r.");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        final String date = request.getParameter("date");
        final Date sqlDate;
        try {
            sqlDate = java.sql.Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            final String message = "wrongdate";
            logger.debug("Empty date.");
            request.setAttribute("message",message);
            final Reader reader = Connections.getFactory().getReaderDao().getReaderById(readerId);
            Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
            request.setAttribute("instAuth", instanceAuthorList);
            request.setAttribute("readerForGive", reader);
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/giveBookRoReader.jsp").forward(request, response);
            return;
        }
        Map.Entry<Instance, List<Author>> instance = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        if (!Connections.getFactory().getOrdersDao().giveBook(readerId,sqlDate,instance.getKey())) {
            logger.error("Troubles with giving book!");
        }
        response.sendRedirect("/allreadersForGivebook");
    }

    /**
     * Take information about instance id and reader id; and forward to addBook form
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null || request.getParameter("id_r") == null ) {
            logger.error("Parametres id_i or id_r or date is null!");
            response.sendError(400);
            return;
        }
        final int instanceId;
        final int readerId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
            readerId = Integer.valueOf(request.getParameter("id_r"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameters id_i or id_r.");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        final Reader reader = Connections.getFactory().getReaderDao().getReaderById(readerId);
        if (reader==null) {
            logger.error("Troubles with idReader");
            response.sendError(400);
            return;
        }
        Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        request.setAttribute("instAuth", instanceAuthorList);
        request.setAttribute("readerForGive", reader);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/giveBookRoReader.jsp").forward(request, response);
    }
}
