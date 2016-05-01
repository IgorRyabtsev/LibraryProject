package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Author;

import java.util.List;

/**
 * Created by igor on 21.04.16.
 */

public interface AuthorDao {
    List<Author> getAll();
    boolean insertAuthor(Author r);
    int findAuthor(Author author);
}
