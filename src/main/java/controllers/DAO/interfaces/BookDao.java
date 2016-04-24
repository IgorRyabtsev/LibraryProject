package main.java.controllers.DAO.interfaces;


import main.java.controllers.model.Book;

import java.util.List;

/**
 * Created by igor on 21.04.16.
 */
public interface BookDao {
    List<Book> getAll();
    Book getBookById(int id);
    boolean deleteBookById(int id);
    boolean insertBook(Book book);
    int findBook(Book book);
}
