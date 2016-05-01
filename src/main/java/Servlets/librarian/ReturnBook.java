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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(request.getParameter("id_i") + "*" + request.getParameter("order"));
        if (request.getParameter("id_i") == null || request.getParameter("order") == null) {
            response.sendError(400);
            return;
        }
        int idOrder;
        int idInstance;
        try {
            idOrder = Integer.valueOf(request.getParameter("order"));
            idInstance = Integer.valueOf(request.getParameter("id_i"));
        } catch (NumberFormatException e) {
            response.sendError(400);
            e.printStackTrace();
            return;
        }

        String date = request.getParameter("date");
        java.sql.Date sqlDate;
        try {
            sqlDate = java.sql.Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            String message = "wrongdate";
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
            response.sendError(400);
            return;
        }
        int idOrder=0;
        int idInstance=0;
        try {
            idOrder = Integer.valueOf(request.getParameter("id"));
            idInstance = Integer.valueOf(request.getParameter("id_i"));
        } catch (NumberFormatException e) {
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
