package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Instance;
import main.java.controllers.model.Orders;

import java.sql.Date;
import java.util.List;

/**
 * Created by igor on 22.04.16.
 */
public interface OrdersDao {
    List<Orders> getAllOrders();
    List<Orders> getOrdersByEmail(String email, boolean isRetDateNull);
    boolean takeBook(int id, Date date, String comments);
    boolean giveBook(int id_r, Date date, Instance instance);
    Date getReleaseDateById(int id);
    Orders getOrderById(int id);
    boolean deleteById(int id);
    boolean setDate(int id, Date rel_date, Date ret_date);
}
