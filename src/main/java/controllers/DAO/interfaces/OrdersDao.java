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
    List<Orders> getOrdersByEmail(String email);
    //вернуть книгу в библиотеку
    boolean takeBook(String email, Date date, Instance instance);
    boolean takeBook(int id, Date date, String comments);
    boolean giveBook(String email, Date date);

}
