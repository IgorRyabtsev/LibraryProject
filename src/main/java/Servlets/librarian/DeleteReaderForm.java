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
import java.util.List;

/**
 * Created by igor on 03.05.16.
 */
@WebServlet(name = "DeleteReaderForm", urlPatterns = "/deleteReaderForm")
public class DeleteReaderForm extends HttpServlet {

    private final Logger logger = Logger.getLogger(DeleteReaderForm.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null!");
            response.sendError(400);
            return;
        }

        int idReader;
        try {
            idReader = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter \"id\"!",e);
            response.sendError(400);
            return;
        }

        Reader reader = Connections.getFactory().getReaderDao().getReaderById(idReader);
        if (reader == null) {
            logger.error("No such reader!");
            response.sendError(400);
            return;
        }

        if (reader.getRole().equals("librarian")) {
            logger.debug("Empty Date");
            String message = "librarian";
            request.setAttribute("message",message);
            List<Orders> ordersByEmail = Connections.getFactory().getOrdersDao().getOrdersByEmail(reader.getEmail(), true);
            request.setAttribute("orders",ordersByEmail);
            request.setAttribute("reader",String.valueOf(idReader));
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/deleteReaderForm.jsp").forward(request, response);
            return;
        }

        if ( !Connections.getFactory().getReaderDao().deleteReaderById(idReader)) {
            logger.error("Troubles with delete reader");
        }
        response.sendRedirect("/deleteReader");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            logger.error("Parameter id is null!");
            response.sendError(400);
            return;
        }

        int idReader;
        try {
            idReader = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            logger.error("Troubles with parameter \"id\"!",e);
            response.sendError(400);
            return;
        }
        Reader readerById = Connections.getFactory().getReaderDao().getReaderById(idReader);

        List<Orders> ordersByEmail = Connections.getFactory().getOrdersDao().getOrdersByEmail(readerById.getEmail(), true);
        request.setAttribute("orders",ordersByEmail);
        request.setAttribute("reader",String.valueOf(idReader));
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/deleteReaderForm.jsp").forward(request, response);
    }
}
