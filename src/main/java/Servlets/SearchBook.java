package main.java.Servlets;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
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
@WebServlet(name = "SearchBook", urlPatterns = "/searchbook")
public class SearchBook extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namef = (String) request.getParameter("namef");
        String names = (String) request.getParameter("names");
        String namep = (String) request.getParameter("namep");
        String bookname = (String) request.getParameter("bookname");
        Author author = new Author(0,namef,names,namep,0);
        Book book = new Book(0,bookname);

        List<Map<Instance, List<Author>>> allInstances = Connections.getFactory().getInstanceDao().getInstanceByCondition(author,book);
        request.setAttribute("instances",allInstances);
    ////    request.getRequestDispatcher("/jsp/avaliablebooks.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/jsp/avaliablebooks.jsp").forward(request, response);
//        response.sendRedirect("/avaliablebooks");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
