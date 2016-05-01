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
@WebServlet(name = "GiveBookForUser", urlPatterns = "/givebook")
public class GiveBookForUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id_i") == null || request.getParameter("id_r") == null || request.getParameter("date") == null) {
            response.sendError(400);
            return;
        }
        int instanceId;
        int readerId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id_i"));
            readerId = Integer.valueOf(request.getParameter("id_r"));
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
            Reader reader = Connections.getFactory().getReaderDao().getReaderById(readerId);
            Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
            request.setAttribute("instAuth", instanceAuthorList);
            request.setAttribute("readerForGive", reader);
//            request.getRequestDispatcher("/jsp/librarian/giveBookRoReader.jsp").forward(request, response);
            request.getRequestDispatcher("/WEB-INF/jsp/librarian/giveBookRoReader.jsp").forward(request, response);
            return;
        }
        Map.Entry<Instance, List<Author>> instance = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        Reader reader = Connections.getFactory().getReaderDao().getReaderById(readerId);

        Connections.getFactory().getOrdersDao().giveBook(readerId,sqlDate,instance.getKey());

//        List<Reader> allReaders = Connections.getFactory().getReaderDao().getAll();
//        request.setAttribute("allReaders",allReaders);
////        request.getRequestDispatcher("/jsp/librarian/allreadersForGivebook.jsp").forward(request, response);//troubles
//        request.getRequestDispatcher("/WEB-INF/jsp/librarian/allreadersForGivebook.jsp").forward(request, response);//troubles
        response.sendRedirect("/allreadersForGivebook");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null || request.getParameter("id_r") == null ) {
            response.sendError(400);
            return;
        }
        int instanceId;
        int readerId;
        try {
            instanceId = Integer.valueOf(request.getParameter("id"));
            readerId = Integer.valueOf(request.getParameter("id_r"));
        } catch (NumberFormatException e) {
            response.sendError(400);
            e.printStackTrace();
            return;
        }
        Reader reader = Connections.getFactory().getReaderDao().getReaderById(readerId);
        Map.Entry<Instance,List<Author>> instanceAuthorList = Connections.getFactory().getInstanceDao().getInstanceById(instanceId);
        request.setAttribute("instAuth", instanceAuthorList);
        request.setAttribute("readerForGive", reader);
//        request.getRequestDispatcher("/jsp/librarian/giveBookRoReader.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/giveBookRoReader.jsp").forward(request, response);
    }
}
