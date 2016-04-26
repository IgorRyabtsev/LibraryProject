package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;

import java.util.List;
import java.util.Map;

/**
 * Created by igor on 21.04.16.
 */
public interface InstanceDao {
    List< Map<Instance, List<Author>> > getAll();
//    List< Map<Instance, List<Author>> > getInstanceByName(String name, int status);
    Map.Entry<Instance,List<Author>> getInstanceById(int id);
    List<Map<Instance, List<Author>>> getInstanceByNameV2(String name, int status);
    List<Map<Instance, List<Author>>> getInstanceByCondition(Author author, Book book);
    boolean insertInstance(Author author, Instance r);
    boolean deleteInstanceById(int id);
}
