package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Instance;

import java.util.List;

/**
 * Created by igor on 22.04.16.
 */
public interface ReaderOrdersDao {
    List<Instance> getListOfOrdersByEmail(String email);

}
