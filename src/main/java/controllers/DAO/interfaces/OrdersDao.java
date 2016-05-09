package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Instance;
import main.java.controllers.model.Orders;

import java.sql.Date;
import java.util.List;

/**
 * Created by igor on 22.04.16.
 */

/**
 * Interface DaoFactory for realization {@link main.java.controllers.DAO.Oracle.OracleOrdersDao}
 * @author igor
 */

public interface OrdersDao {
    /**
     * Get list of orders
     * @return list of orders
     */
    List<Orders> getAllOrders();

    /**
     * Get order by email
     * @param email reader email
     * @param isRetDateNull if we need return date == null, then =true, else false
     * @return list of orders
     */
    List<Orders> getOrdersByEmail(String email, boolean isRetDateNull);

    /**
     * Take book from reader
     * @param id order id
     * @param date returning date
     * @param comments comments by book
     * @return true if taking is ok, else false
     */
    boolean takeBook(int id, Date date, String comments);

    /**
     * Give book to reader
     * @param id_r reader id
     * @param date release date
     * @param instance instance
     * @return true if everything is ok, else false
     */
    boolean giveBook(int id_r, Date date, Instance instance);

    /**
     * Return release date by order id
     * @param id order id
     * @return release date
     */
    Date getReleaseDateById(int id);

    /**
     * Get order by id
     * @param id order id
     * @return {@link Orders}
     */
    Orders getOrderById(int id);

    /**
     * Delete order by id
     * @param id order id
     * @return true if everything is ok, else false
     */
    boolean deleteById(int id);

    /**
     * Set release and return date
     * @param id order id
     * @param rel_date release date
     * @param ret_date return date
     * @return true if everything is ok, else false
     */
    boolean setDate(int id, Date rel_date, Date ret_date);
}
