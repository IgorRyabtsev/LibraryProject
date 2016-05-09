package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;

import java.util.List;
import java.util.Map;

/**
 * Created by igor on 21.04.16.
 */
/**
 * Interface DaoFactory for realization {@link main.java.controllers.DAO.Oracle.OracleInstanceDao}
 * @author igor
 */

public interface InstanceDao {
    /**
     * Return list of instances with authors
     * @return list of instances
     */
    List< Map<Instance, List<Author>> > getAll();

    /**
     * Get Instance by id
     * @param id instance id
     * @return pair of instance-authors
     */
    Map.Entry<Instance,List<Author>> getInstanceById(int id);

    /**
     * Get Instance by name of book
     * @param name book name
     * @param status book status=1 if exist in library, else 0
     * @return list of instances
     */
    List<Map<Instance, List<Author>>> getInstanceByNameV2(String name, int status);

    /**
     * Get Instance by condition: by author and book name
     * @param author author
     * @param book book
     * @return  list of instances
     */
    List<Map<Instance, List<Author>>> getInstanceByCondition(Author author, Book book);

    /**
     * Getting List of Authors by instance
     * @param instance instance
     * @return list of authors
     */
    List<Author> getListOfAuthors(Instance instance);

    /**
     * Insert new Instance
     * @param author author
     * @param instance instance
     * @return true if everything is ok, else false
     */
    boolean insertInstance(Author author, Instance instance);

    /**
     * Insert Instance by id
     * @param id instance id
     * @return true if everything is ok, else false
     */
    boolean deleteInstanceById(int id);
}
