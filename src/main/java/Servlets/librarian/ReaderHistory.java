package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Orders;
import main.java.controllers.model.Reader;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 28.04.16.
 */
@WebServlet(name = "ReaderHistory", urlPatterns = "/readerhistory")
public class ReaderHistory extends HttpServlet {

    private final Logger logger = Logger.getLogger(ReaderHistory.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
            logger.error("Troubles with parameter id.");
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Reader reader = Connections.getFactory().getReaderDao().getReaderById(idReader);
        List<Orders> ordersByEmail = Connections.getFactory().getOrdersDao().getOrdersByEmail(reader.getEmail(),false);
        List<Map.Entry<Orders,List<Author>> > history = new ArrayList<>();
        for (Orders orders:ordersByEmail) {
            Map.Entry<Orders,List<Author>> instance= new AbstractMap.SimpleEntry<>(orders,Connections.getFactory().getInstanceDao().getListOfAuthors(orders.getInstance()));
            history.add(instance);
        }
        request.setAttribute("history",history);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/readerhistory.jsp").forward(request, response);
    }
}
