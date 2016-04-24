package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Author;

import java.util.List;

/**
 * Created by igor on 21.04.16.
 */
//TODO: добавить поиск по критерию.
public interface AuthorDao {
    List<Author> getAll();
    Author getAuthorById(int id);
    boolean deleteAuthorById(int id);
    boolean insertAuthor(Author r);
    int findAuthor(Author author);
}
