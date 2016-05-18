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
 * Created by igor on 26.04.16.
 */

/**
 * Servlet for get all avaliable books
 * @author igor
 */
@WebServlet(name = "AllAvaliaBooks", urlPatterns = "/avaliablebooks")
public class AllAvaliaBooks extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Get all available books
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<Instance, List<Author>>> allInstances = Connections.getFactory().getInstanceDao().getInstanceByName(null,1);
        request.setAttribute("instances",allInstances);
        request.getRequestDispatcher("/WEB-INF/jsp/avaliablebooks.jsp").forward(request, response);
    }
}
