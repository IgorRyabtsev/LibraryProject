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
public interface ReaderOrdersDao {
    List<Instance> getListOfOrdersByEmail(String email);
    boolean insertOrder(Reader reader, Instance instance);
    List<Map.Entry<Instance, List<Author> >> getInstancesByReader(Reader r);
    boolean deleteOrderByReader(Reader reader, Book book, String publish);
}
