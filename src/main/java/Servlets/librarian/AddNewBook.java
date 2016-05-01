package main.java.Servlets.librarian;

import main.java.controllers.DAO.Connections;
import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by igor on 27.04.16.
 */
@WebServlet(name = "AddNewBook", urlPatterns = "/addnewbook")
public class AddNewBook extends HttpServlet {

    private final Logger logger = Logger.getLogger(AddNewBook.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String namef = request.getParameter("namef");
        String names = request.getParameter("names");
        String namep = request.getParameter("namep");
        String yearbirth = request.getParameter("yearbirth");
        String year = request.getParameter("year");
        String bookname = request.getParameter("bookname");
        String publish = request.getParameter("publish");
        String cost = request.getParameter("cost");
        //some empty values
        if(namef.trim().isEmpty() || names.trim().isEmpty()  ||
                bookname.trim().isEmpty() || publish.trim().isEmpty() || year.trim().isEmpty() ||
                cost.trim().isEmpty()) {
            logger.debug("Some empty fields.");
            String message="enterAllValues";
            request.setAttribute("message",message);
            doGet(request, response);
            return;
        }
        Integer yearBirth=0;
        if(!yearbirth.isEmpty()) {
            try {
                yearBirth = new Integer(yearbirth);
            } catch (NumberFormatException e) {
                logger.debug("Not correct year of birth format.");
                request.setAttribute("message", "enterRightYearofBirth");
                doGet(request, response);
                return;
            }
            if( yearBirth > getCurrentYear() || yearBirth < getCurrentYear()-1000){
                logger.debug("Not correct year of birth.");
                request.setAttribute("message", "enterRightYearofBirth");
                doGet(request, response);
                return;
            }
        }
//        wrong year format
        Integer yearBook;
        try {
            yearBook = new Integer(year);
        }catch (NumberFormatException e) {
            logger.debug("Not correct year of birth format.");
            request.setAttribute("message","enterRightBookYear");
            doGet(request, response);
            return;
        }

        Integer bookCost;
        try {
            bookCost = new Integer(cost);
        }catch (NumberFormatException e) {
            logger.debug("Not correct cost format.");
            request.setAttribute("message","enterRightCost");
            doGet(request, response);
            return;
        }
        if(namep.isEmpty()) namep=" ";//for insertion
        if (Connections.getFactory().getInstanceDao().insertInstance(new Author(0,namef,names,namep,yearBirth),
                new Instance(0,new Book(0,bookname),yearBook,publish,bookCost,1,""))) {
            logger.debug("Correct insertion");
            request.setAttribute("message", "ok");
            doGet(request, response);
            return;
        }
        logger.error("Not correct insertion!");
        response.sendError(400);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/addnewbook.jsp").forward(request, response);
    }

    private static int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }
}
