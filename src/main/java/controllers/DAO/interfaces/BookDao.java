package main.java.controllers.DAO.interfaces;


import main.java.controllers.model.Book;

import java.util.List;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Interface BookDao for realization {@link main.java.controllers.DAO.Oracle.OracleBookDao}
 * @author igor
 */

public interface BookDao {

    /**
     * Get list of {@link Book}
     * @return list of book
     */
    List<Book> getAll();

    /**
     * Insert new book
     * @param book book
     * @return true if ok, else false
     */
    boolean insertBook(Book book);

    /**
     * Finding book
     * @param book book
     * @return book id
     */
    int findBook(Book book);
}
