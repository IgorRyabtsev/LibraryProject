package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Author;
import main.java.controllers.model.Instance;

import java.util.List;
import java.util.Map;

/**
 * Created by igor on 21.04.16.
 */
public interface InstanceDao {
    List< Map<Instance, List<Author>> > getAll();
    List< Map<Instance, List<Author>> > getInstanceByName(String name, int status);
    public List<Map<Instance, List<Author>>> getInstanceByNameV2(String name, int status);
    boolean insertInstance(Author author, Instance r);
    boolean deleteInstanceById(int id);
}
