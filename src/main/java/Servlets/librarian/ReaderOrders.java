package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 28.04.16.
 */
@WebServlet(name = "ReaderOrders", urlPatterns = "/readerorders")
public class ReaderOrders extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendError(400);
            return;
        }

        int idReader;
        try {
            idReader = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Reader readerById = Connections.getFactory().getReaderDao().getReaderById(idReader);
        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReaderForLibrarian(readerById);
        System.out.println(instancesByReader);
        request.setAttribute("instances",instancesByReader);
        request.setAttribute("readers",readerById);
//        request.getRequestDispatcher("/jsp/librarian/userorders.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/userorders.jsp").forward(request, response);
    }
}
