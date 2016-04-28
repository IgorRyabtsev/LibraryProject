package main.java.Servlets;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 26.04.16.
 */
@WebServlet(name = "UserOrders", urlPatterns = "/userorders")
public class UserOrders extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Reader reader = (Reader) request.getSession().getAttribute("user_session");
        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReaderForLibrarian(reader);
        request.setAttribute("instances",instancesByReader);
        request.getRequestDispatcher("/jsp/userorders.jsp").forward(request, response);
    }
}
