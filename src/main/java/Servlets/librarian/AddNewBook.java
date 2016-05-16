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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 27.04.16.
 */

/**
 * Servlet for adding new book
 * @author igor
 */
@WebServlet(name = "AddNewBook", urlPatterns = "/addnewbook")
public class AddNewBook extends HttpServlet {

    private final Logger logger = Logger.getLogger(AddNewBook.class);

    /**
     * Add new book by parametres
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("namef") == null || request.getParameter("names") == null ||
                request.getParameter("namep") == null || request.getParameter("yearbirth") == null ||
                request.getParameter("year") == null || request.getParameter("bookname") == null ||
                request.getParameter("publish") == null || request.getParameter("cost") == null) {
            logger.error("Troubles with parametres!");
            response.sendError(400);
            return;
        }

        final String[] namef = (request.getParameter("namef")).split(";");
        final String[] names = request.getParameter("names").split(";");
        final String[] namep = request.getParameter("namep").split(";");
        final String[] yearbirth = request.getParameter("yearbirth").split(";");
        final String year = request.getParameter("year");
        final String bookname = request.getParameter("bookname");
        final String publish = request.getParameter("publish");
        final String cost = request.getParameter("cost");
        //not correct data
        if(namef.length != namep.length || namep.length!=names.length || names.length!=yearbirth.length) {
            logger.debug("Not correct data input .");
            String message="entercorrectData";
            request.setAttribute("message",message);
            doGet(request, response);
            return;
        }

        //if no data inside namef || names
        if(!checkNames(namef,names)) {
            logger.debug("Some empty fields.");
            String message="enterAllValues";
            request.setAttribute("message",message);
            doGet(request, response);
            return;
        }

        List<String> nameP = getPatronymic(namep);
        //some empty values
        if(bookname.trim().isEmpty() || publish.trim().isEmpty() || year.trim().isEmpty() ||
                cost.trim().isEmpty()) {
            logger.debug("Some empty fields.");
            String message="enterAllValues";
            request.setAttribute("message",message);
            doGet(request, response);
            return;
        }

        List<Integer> years = getYears(yearbirth);
        if(years == null) {
            logger.debug("Not correct year of birth format.");
            request.setAttribute("message", "enterRightYearofBirth");
            doGet(request, response);
            return;
        }
        if(!checkYears(years)) {
            logger.debug("Not correct year of birth.");
            request.setAttribute("message", "enterRightYearofBirth");
            doGet(request, response);
            return;
        }

//        wrong year format
        final Integer yearBook;
        try {
            yearBook = new Integer(year);
        }catch (NumberFormatException e) {
            logger.debug("Not correct year of birth format.");
            request.setAttribute("message","enterRightBookYear");
            doGet(request, response);
            return;
        }

        final Integer bookCost;
        try {
            bookCost = new Integer(cost);
        }catch (NumberFormatException e) {
            logger.debug("Not correct cost format.");
            request.setAttribute("message","enterRightCost");
            doGet(request, response);
            return;
        }

        List<Author> authors = getAuthors(namef, names, nameP, years);

        if (Connections.getFactory().getInstanceDao().insertInstance(authors,yearBook, bookname, publish, bookCost)) {
            logger.debug("Correct insertion");
            request.setAttribute("message", "ok");
            doGet(request, response);
            return;
        }
        logger.error("Not correct insertion!");
        response.sendError(400);
    }

    /**
     * Forwarding to ann new book form
     * @param request request
     * @param response response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/librarian/addnewbook.jsp").forward(request, response);
    }

    /**
     * Getting current year
     * @return current year
     */
    private static int getCurrentYear() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        return calendar.get(java.util.Calendar.YEAR);
    }

    /**
     * Check correct names
     * @param nameFirst [] of first names
     * @param nameSecond [] of second names
     * @return true if correct names, else false
     */

    private boolean checkNames(String[] nameFirst, String[] nameSecond) {
        int countNameFirst=0;
        int countNameSecond=0;
        for (String namef:nameFirst) {
            if (!namef.trim().equals("")) {
                ++countNameFirst;
            }
        }
        if (nameFirst.length!=countNameFirst) {
            return false;
        }

        for (String names:nameSecond) {
            if (!names.trim().equals("")) {
                ++countNameSecond;
            }
        }
        if (nameSecond.length!=countNameSecond) {
            return false;
        }
        return true;
    }

    /**
     * Get list of years
     * @param yearbirth array of years
     * @return list of years
     */
    private List<Integer> getYears(String[] yearbirth) {
        Integer yearBirth;
        List<Integer> yearList=new ArrayList<>();
        for (String years:yearbirth) {
            try {
                yearBirth = new Integer(years);
                yearList.add(yearBirth);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return yearList;
    }

    /**
     * Check years
     * @param years years
     * @return true if correct, else false
     */
    private boolean checkYears(List<Integer> years) {
        for (Integer year:years) {
            if(year==0) {
                continue;
            }
            if( year > getCurrentYear() || year < getCurrentYear()-1000){
                return false;
            }
        }
        return true;
    }

    /**
     * get list of patronymic
     * @param namep array of patronymic
     * @return list of patronymics
     */
    private List<String> getPatronymic(String[] namep) {
        List<String> nameP=new ArrayList<>();
        for (String name:namep) {
            if (name.trim().equals("")) {
                nameP.add(" ");
                continue;
            }
            nameP.add(name);
        }
        return nameP;
    }

    /**
     * Get list of authors from from arrays and lists
     * @param namef array of first names
     * @param names array of second names
     * @param nameP list of patronymics
     * @param years list of years
     * @return list of authors
     */
    private List<Author> getAuthors(String[] namef, String[] names, List<String> nameP, List<Integer> years) {
        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < namef.length; i++) {
            authors.add(new Author(0,namef[i],names[i],nameP.get(i),years.get(i)));
        }
        return authors;
    }

}
