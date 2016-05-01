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

//        if (request.getParameter("id_i") == null || request.getSession().getAttribute("order") == null) {
//            response.sendError(400);
//            return;
//        }
//
//        int idOrder=0;
//        int idInstance=0;
//        try {
//            idOrder = (Integer) (request.getSession().getAttribute("order"));
//            idInstance = Integer.valueOf(request.getParameter("id_i"));
//        } catch (NumberFormatException e) {
//            request.getSession().setAttribute("order",null);
//            response.sendError(400);
//            e.printStackTrace();
//            return;
//        }
//
//        String date = request.getParameter("date");
//        java.sql.Date sqlDate;
//        try {
//            sqlDate = java.sql.Date.valueOf(date);
//        } catch (IllegalArgumentException e) {
//            String message = "wrongdate";
//            request.setAttribute("message",message);
//            Map.Entry<Instance, List<Author>> instanceById = Connections.getFactory().getInstanceDao().getInstanceById(idInstance);
//            request.setAttribute("instAuth",instanceById);
//            request.getRequestDispatcher("/jsp/librarian/returnbook.jsp").forward(request, response);
//            return;
//        }
//        String comments= request.getParameter("comments");
//
//        Connections.getFactory().getOrdersDao().takeBook(idOrder,sqlDate,comments);
//        request.getSession().setAttribute("order",null);
//        List<Reader> allReaders = Connections.getFactory().getReaderDao().getAll();
//        request.setAttribute("allReaders",allReaders);
//        request.getRequestDispatcher("/jsp/librarian/allReaders.jsp").forward(request, response);
        System.out.println(request.getParameter("id_i") + "*" + request.getParameter("order"));
        if (request.getParameter("id_i") == null || request.getParameter("order") == null) {
            System.out.println("1");
            response.sendError(400);
            return;
        }

        int idOrder=0;
        int idInstance=0;
        try {
            idOrder = Integer.valueOf(request.getParameter("order"));
            idInstance = Integer.valueOf(request.getParameter("id_i"));
        } catch (NumberFormatException e) {
//            request.getSession().setAttribute("order",null);
            System.out.println("2");
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
//            request.getRequestDispatcher("/jsp/librarian/returnbook.jsp").forward(request, response);
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/returnbook.jsp").forward(request, response);
            return;
        }
        String comments= request.getParameter("comments");

        Connections.getFactory().getOrdersDao().takeBook(idOrder,sqlDate,comments);
//        request.getSession().setAttribute("order",null);
        List<Reader> allReaders = Connections.getFactory().getReaderDao().getAll();
//        request.setAttribute("allReaders",allReaders);
//        request.getRequestDispatcher("/jsp/librarian/allReaders.jsp").forward(request, response);
//        request.getRequestDispatcher("/WEB-INF/jsp/librarian/allReaders.jsp").forward(request, response);
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
        System.out.println(request.getParameter("id"));
        System.out.println(request.getParameter("id_i"));

        Map.Entry<Instance, List<Author>> instanceById = Connections.getFactory().getInstanceDao().getInstanceById(idInstance);
//        request.getSession().setAttribute("order",idOrder);
        request.setAttribute("order",request.getParameter("id"));
        request.setAttribute("instAuth",instanceById);
//        request.getRequestDispatcher("/jsp/librarian/returnbook.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/returnbook.jsp").forward(request, response);
    }
}
