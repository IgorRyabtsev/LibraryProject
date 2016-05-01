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
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 26.04.16.
 */
@WebServlet(name = "DeleteUserOrders", urlPatterns = "/deleteuserorder")
public class DeleteUserOrders extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendError(400);
            return;
        }
        if (request.getParameter("publish") == null) {
            response.sendError(400);
            return;
        }


        int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        String publish = request.getParameter("publish");
        Map.Entry<Instance, List<Author>> instanceById = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        Reader reader = (Reader) request.getSession().getAttribute("user_session");

        Connections.getFactory().getReaderOrdersDao().deleteOrderByReader(reader,instanceById.getKey().getBook(),publish);

        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReader(reader);
        if(instancesByReader == null) {
            response.sendError(400);
            return;
        }
        response.sendRedirect("/userorders");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendError(400);
            return;
        }

        int instanceId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        Reader reader = (Reader) request.getSession().getAttribute("user_session");
        List<Map.Entry<Instance, List<Author>>> instancesByReader = Connections.getFactory().getReaderOrdersDao().getInstancesByReader(reader);
        if(instancesByReader == null) {
            response.sendError(400);
            return;
        }

        Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        request.setAttribute("instAuth", instanceAuthorList);
        request.getRequestDispatcher("/WEB-INF/jsp/deleteuserorders.jsp").forward(request, response);
    }
}