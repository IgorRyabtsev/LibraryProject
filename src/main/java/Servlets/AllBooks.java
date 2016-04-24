package main.java.Servlets;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 24.04.16.
 */
@WebServlet(name = "AllBooks", urlPatterns = "/all_books")
public class AllBooks extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<Instance, List<Author>>> allInstances = Connections.getFactory().getInstanceDao().getAll();
        request.setAttribute("instances",allInstances);

        request.getRequestDispatcher("/jsp/all_books.jsp").forward(request, response);
    }
}
