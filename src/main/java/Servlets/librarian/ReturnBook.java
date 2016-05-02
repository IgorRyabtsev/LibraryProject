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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 28.04.16.
 */
@WebServlet(name = "ReturnBook", urlPatterns = "/returnbook")
public class ReturnBook extends HttpServlet {

    private final Logger logger = Logger.getLogger(ReturnBook.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id_i") == null || request.getParameter("order") == null) {
            logger.error("Parametres id_i or order is null!");
            response.sendError(400);
            return;
        }
        int idOrder;
        int idInstance;
        try {
            idOrder = Integer.valueOf(request.getParameter("order"));
            idInstance = Integer.valueOf(request.getParameter("id_i"));
        } catch (NumberFormatException e) {
            logger.error("Troubles wirh \"order\" or \"id\"");
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        String date = request.getParameter("date");
        java.sql.Date sqlDate;
        try {
            sqlDate = java.sql.Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            logger.debug("Empty Date");
            String message = "wrongdate";
            request.setAttribute("message",message);
            Map.Entry<Instance, List<Author>> instanceById = Connections.getFactory().getInstanceDao().getInstanceById(idInstance);
            request.setAttribute("instAuth",instanceById);
            request.setAttribute("order",request.getParameter("order"));
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/returnbook.jsp").forward(request, response);
            return;
        }
        Date releaseDateById = Connections.getFactory().getOrdersDao().getReleaseDateById(idOrder);

        if (releaseDateById.getTime() > sqlDate.getTime()) {
            logger.debug("Not correct date.");
            String message = "releasedatebigger";
            request.setAttribute("message",message);
            Map.Entry<Instance, List<Author>> instanceById = Connections.getFactory().getInstanceDao().getInstanceById(idInstance);
            request.setAttribute("instAuth",instanceById);
            request.setAttribute("order",request.getParameter("order"));
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/returnbook.jsp").forward(request, response);
            return;
        }

        String comments= request.getParameter("comments");
        Connections.getFactory().getOrdersDao().takeBook(idOrder,sqlDate,comments);
        response.sendRedirect("/allreaders");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("id") == null || request.getParameter("id_i") == null) {
            logger.error("Parametres id_i or id is null!");
            response.sendError(400);
            return;
        }
        int idOrder;
        int idInstance;
        try {
            idOrder = Integer.valueOf(request.getParameter("id"));
            idInstance = Integer.valueOf(request.getParameter("id_i"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with \"id_i\" or \"id\" is null!");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Map.Entry<Instance, List<Author>> instanceById = Connections.getFactory().getInstanceDao().getInstanceById(idInstance);
        request.setAttribute("order",String.valueOf(idOrder));
        request.setAttribute("instAuth",instanceById);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/returnbook.jsp").forward(request, response);
    }
}
