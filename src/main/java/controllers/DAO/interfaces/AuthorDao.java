package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Author;

import java.util.List;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Interface for realization {@link main.java.controllers.DAO.Oracle.OracleAuthorDao}
 * @author igor
 */
public interface AuthorDao {

    /**
     * Get list of {@link Author}
     * @return list of authors
     */
    List<Author> getAll();

    /**
     * Insert new author
     * @param author auhtor
     * @return true if everything is ok, else false
     */
    boolean insertAuthor(Author author);

    /**
     * Find author
     * @param author author
     * @return author id
     */
    int findAuthor(Author author);
}
