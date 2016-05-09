package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Reader;

import java.util.List;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Interface DaoFactory for realization {@link main.java.controllers.DAO.Oracle.OracleReaderDao}
 * @author igor
 */

public interface ReaderDao {
    /**
     * Get list of readers
     * @return list of {@link Reader}
     */
    List<Reader> getAll();

    /**
     * Get reader by id
     * @param id reader id
     * @return {@link Reader} by id
     */
    Reader getReaderById(int id);

    /**
     * Get reader by email
     * @param email reader's email
     * @return {@link Reader} by email
     */
    Reader getReaderByEmail(String email);

    /**
     * Insert new {@link Reader}
     * @param r - reader
     * @return true if insertion ok, else false
     */
    boolean insertReader(Reader r);

    /**
     * Make reader role librarian
     * @param id reader id
     * @return true if everything ok, else false
     */
    boolean makeLibrarian(int id);

    /**
     * Delete {@link Reader} by id
     * @param id reader id
     * @return true if deleting is ok, else false
     */
    boolean deleteReaderById(int id);
}
