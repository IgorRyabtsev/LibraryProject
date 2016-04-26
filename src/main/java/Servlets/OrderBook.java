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
@WebServlet(name = "OrderBook",urlPatterns = "/orderbook")
public class OrderBook extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        Map.Entry<Instance,List<Author>> instance =  Connections.getFactory().getInstanceDao().getInstanceById(instanceId);

        if (instance == null) {
            response.sendError(400);
            return;
        }

        if (!Connections.getFactory().getReaderOrdersDao().insertOrder(reader,instance.getKey())) {
            response.sendError(400);
            return;
        }

        List<Map<Instance, List<Author>>> allInstances = Connections.getFactory().getInstanceDao().getInstanceByNameV2(null,1);
        request.setAttribute("instances",allInstances);
        request.getRequestDispatcher("/jsp/avaliablebooks.jsp").forward(request, response);
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
        Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        request.setAttribute("instAuth", instanceAuthorList);
        request.getRequestDispatcher("/jsp/orderbook.jsp").forward(request, response);
    }
}
