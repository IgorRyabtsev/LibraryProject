package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Reader;

import java.util.List;

/**
 * Created by igor on 22.04.16.
 */
public interface ReaderOrdersDao {
    List<Instance> getListOfOrdersByEmail(String email);
    boolean insertOrder(Reader reader, Instance instance);
}
