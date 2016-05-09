package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Reader;

import java.util.List;
import java.util.Map;

/**
 * Created by igor on 22.04.16.
 */

/**
 * Interface DaoFactory for realization {@link main.java.controllers.DAO.Oracle.OracleReaderOrdersDao}
 * @author igor
 */

public interface ReaderOrdersDao {
    /**
     * Return list of {@link Instance} - not given instances by email
     * @param email reader email
     * @return list og Instances
     */
    List<Instance> getListOfOrdersByEmail(String email);

    /**
     * Insert new order into OrdNum table
     * @param reader {@link Reader}
     * @param instance {@link Instance}
     * @return true if insertion is ok, else false
     */
    boolean insertOrder(Reader reader, Instance instance);

    /**
     * Return list of instances, that orders {@link Reader}
     * @param r {@link Reader}
     * @return list of instances
     */
    List<Map.Entry<Instance, List<Author> >> getInstancesByReader(Reader r);

    /**
     * Delete order from OrdNum table
     * @param reader {@link Reader}
     * @param book {@link Book}
     * @param publish publish of book
     * @return true if deleting is ok, else false
     */
    boolean deleteOrderByReader(Reader reader, Book book, String publish);

    /**
     * Get list of orders by email
     * @param email reader email
     * @return list of instances
     */
    List<Instance> getListOfOrdersByEmailForLibrarian(String email);

    /**
     * Get list of instances by reader from OrdNum table
     * @param r {@link Reader}
     * @return list of instances
     */
    List<Map.Entry<Instance, List<Author>>> getInstancesByReaderForLibrarian(Reader r);
}
