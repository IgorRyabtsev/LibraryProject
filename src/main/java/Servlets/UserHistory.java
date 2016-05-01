package main.java.Servlets;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Orders;
import main.java.controllers.model.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by igor on 26.04.16.
 */
@WebServlet(name = "UserHistory", urlPatterns = "/userhistory")
public class UserHistory extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Reader reader = (Reader) request.getSession().getAttribute("user_session");
        List<Orders> ordersByEmail = Connections.getFactory().getOrdersDao().getOrdersByEmail(reader.getEmail());
        request.setAttribute("history",ordersByEmail);
        request.getRequestDispatcher("/WEB-INF/jsp/userhistory.jsp").forward(request, response);
    }
}
