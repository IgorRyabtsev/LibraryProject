package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.Connections;
import main.java.controllers.DAO.interfaces.InstanceDao;
import main.java.controllers.model.AuthBook;
import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Class for working with DB table Instance
 * @author igor
 */

public class OracleInstanceDao implements InstanceDao {

    private static final Logger logger = Logger.getLogger(OracleInstanceDao.class);

    /**
     * Get Instance by id
     * @param id instance id
     * @return pair of instance-authors or null (if something goes wrong return null)
     */
    @Override
    public Map.Entry<Instance,List<Author>> getInstanceById(int id) {
        Instance instance = new Instance();
        List<Author> authors = new ArrayList<>();
        logger.debug("getInstaceById get instance by id.");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_i, id_book, year_b, publish, cost, status, comments, name_b FROM Instance join Book on " +
                            "id_book=id_b where id_i=" + id)) {
            while (rs.next()) {
                instance = new Instance(rs.getInt("id_i"),
                                new Book(rs.getInt("id_book"),rs.getString("name_b")),
                                rs.getInt("year_b"),
                                rs.getString("publish"),
                                rs.getInt("cost"),
                                rs.getInt("status"),
                                rs.getString("comments"));
            }
        } catch (SQLException e) {
            logger.error("SQLException getInstaceById", e);
        }

        if(instance.getPublish()==null) {
            return null;
        }
        logger.debug("getInstaceById get authors.");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT distinct id_a, name_f, name_s, name_p, year_a  FROM " +
                            "((Instance join AuBook on id_book=book_id) join Author on id_a=author_id) " +
                            "where id_book = " + instance.getBook().getId_b())) {
            while (rs.next()) {
                authors.add(new Author(rs.getInt("id_a"),
                        rs.getString("name_f"),
                        rs.getString("name_s"),
                        rs.getString("name_p"),
                        rs.getInt("year_a")));
            }
        } catch (SQLException e) {
            logger.error("SQLException getInstaceById get authors", e);
        }

        if(authors.isEmpty()) {
            logger.error("No authors");
            return null;
        }
        Map.Entry<Instance,List<Author>> instAuth = new AbstractMap.SimpleEntry<>(instance, authors);
        return instAuth;
    }

    /**
     * Return list of instances with authors
     * @return list of instances
     */
    @Override
    public List<Map<Instance, List<Author>>> getAll() {
        return getInstanceByNameV2(null,0);
    }

    /**
     * Get Instance by name of book
     * @param name book name
     * @param status book status=1 if exist in library, else 0
     * @return list of instances
     */
    @Override
    public List<Map<Instance, List<Author>>> getInstanceByNameV2(String name, int status) {
        String additional;
        if(name == null) {
            additional="";
        } else {
            additional = "where name_b = '"+ name +"' and status  " + ((status==1) ? "=1" : ">=0");
        }
        List<Map<Instance, List<Author>>> instances = new ArrayList<>();

        List<Instance> inst = new ArrayList<>();
        logger.debug("getInstanceByName get list of instances");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_i, id_book, year_b, publish, cost, status, comments, name_b FROM Instance join Book on " +
                            "id_book=id_b " + additional)) {
            while (rs.next()) {
                inst.add(new Instance(rs.getInt("id_i"),
                        new Book(rs.getInt("id_book"),rs.getString("name_b")),
                        rs.getInt("year_b"),
                        rs.getString("publish"),
                        rs.getInt("cost"),
                        rs.getInt("status"),
                        rs.getString("comments")));
            }
        } catch (SQLException e) {
            logger.error("SQLException getInstacesByName", e);
        }
        //if no instances
        if(inst.isEmpty()) {
            return new ArrayList<>();
        }

        List<AuthBook> authbooks=new ArrayList<>();
        logger.debug("getInstanceByName get list Authors-Books");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a, id_b, name_b  FROM ((Book join AuBook on book_id=id_b) join Author on " +
                            "id_a=author_id)")) {
            while (rs.next()) {
                authbooks.add(new AuthBook
                        (new Author(rs.getInt("id_a"),
                                rs.getString("name_f"),
                                rs.getString("name_s"),
                                rs.getString("name_p"),
                                rs.getInt("year_a")),
                                new Book(rs.getInt("id_b"),
                                        rs.getString("name_b"))));
            }
        } catch (SQLException e) {
            logger.error("SQLException getInstanceByName get list of Author-Books",e);
        }

        if(authbooks.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Instance, List<Author>> mapinst;
        List<Author> auth;
        for (Instance instance: inst) {
            auth = new ArrayList<>();
            mapinst = new HashMap<>();
            for (AuthBook au:authbooks) {
                if(instance.getBook().getId_b()==au.getBook().getId_b()) {
                    auth.add(au.getAuthor());
                }
            }
            mapinst.put(instance,auth);
            instances.add(mapinst);
        }
        return instances;
    }

    /**
     * Get Instance by condition: by author and book name
     * @param author author
     * @param book book
     * @return  list of instances
     */
    @Override
    public List<Map<Instance, List<Author>>> getInstanceByCondition(Author author, Book book) {
        String additionalForBook;
        String additionalForAuthors;
        additionalForBook=parseForBook(book);
        additionalForAuthors=parseForAuthors(author);
        List<Map<Instance, List<Author>>> instances = new ArrayList<>();

        List<Instance> inst = new ArrayList<>();
        logger.debug("getInstanceByCondition get list of instances");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_i, id_book, year_b, publish, cost, status, comments, name_b FROM (Instance join Book on " +
                            "id_book=id_b) " + additionalForBook+" 1=1 and id_b in " +
                            "(select au.book_id from ((Author auth join AuBook au on auth.id_a=au.author_id) join Instance inst on " +
                            "inst.id_book=au.book_id )" +
                            additionalForAuthors + " 1=1)")) {
            while (rs.next()) {
                inst.add(new Instance(rs.getInt("id_i"),
                        new Book(rs.getInt("id_book"),rs.getString("name_b")),
                        rs.getInt("year_b"),
                        rs.getString("publish"),
                        rs.getInt("cost"),
                        rs.getInt("status"),
                        rs.getString("comments")));
            }
        } catch (SQLException e) {
            logger.error("SQLException getInstacesByCondition", e);;
        }
        //if no instances
        if(inst.isEmpty()) {
            return new ArrayList<>();
        };

        List<AuthBook> authbooks=new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a, id_b, name_b  FROM ((Book join AuBook on book_id=id_b) join Author on " +
                            "id_a=author_id) " + additionalForAuthors + " 1=1")) {
            while (rs.next()) {
                authbooks.add(new AuthBook
                        (new Author(rs.getInt("id_a"),
                                rs.getString("name_f"),
                                rs.getString("name_s"),
                                rs.getString("name_p"),
                                rs.getInt("year_a")),
                                new Book(rs.getInt("id_b"),
                                        rs.getString("name_b"))));
            }
        } catch (SQLException e) {
            logger.error("SQLException getInstacesByCondition", e);
        }

        if(authbooks.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Instance, List<Author>> mapinst;
        List<Author> auth;
        for (Instance instance: inst) {
            auth = new ArrayList<>();
            mapinst = new HashMap<>();
            for (AuthBook au : authbooks) {
                if (instance.getBook().getId_b() == au.getBook().getId_b()) {
                    auth.add(au.getAuthor());
                }
            }
            mapinst.put(instance, auth);
            instances.add(mapinst);
        }
        return instances;
    }

    /**
     * Getting List of Authors by instance
     * @param instance instance
     * @return list of authors
     */
    @Override
    public  List<Author> getListOfAuthors(Instance instance) {
        List<Author> authors = new ArrayList<>();
        logger.debug("getListOfAuthors");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a, id_b, name_b  FROM ((Book join AuBook on book_id=id_b) " +
                            "join Author on " +
                            "id_a=author_id) where id_b=" + instance.getBook().getId_b())) {
            while (rs.next()) {
                authors.add(new Author(rs.getInt("id_a"),
                                rs.getString("name_f"),
                                rs.getString("name_s"),
                                rs.getString("name_p"),
                                rs.getInt("year_a")));
            }
        } catch (SQLException e) {
            logger.error("SQLException getListOfAuthors", e);
        }
        return authors;
    }

    /**
     * Parse Author in sql expression
     * @param author author
     * @return part of sql expression
     */
    private String parseForAuthors(Author author) {
        StringBuffer expression = new StringBuffer(" where ");
        String tmp;
        if(!(tmp = author.getName_f()).isEmpty()) expression.append(" name_f='" + tmp + "' and");
        if(!(tmp = author.getName_s()).isEmpty()) expression.append(" name_s='" + tmp + "' and");
        if(!(tmp = author.getName_p()).isEmpty()) expression.append(" name_p='" + tmp + "' and");
        return expression.toString();
    }

    /**
     * Parse Book in sql expression
     * @param book book
     * @return part of sql expression
     */
    private String parseForBook(Book book) {
        StringBuffer expression = new StringBuffer(" where ");
        String tmp;
        if(!(tmp = book.getName_b()).isEmpty()) expression.append(" name_b='" + tmp + "' and ");
        return expression.toString();
    }

    /**
     * Insert Instance by id
     * @param id instance id
     * @return true if everything is ok, else false
     */
    @Override
    public boolean deleteInstanceById(int id) {
        logger.debug("Delete instance");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){
            return statement.executeUpdate(
                    "Delete from Instance where id_i = " + id ) > 0;
        } catch (SQLException e) {
            logger.error("SQLException deleteInstanceById", e);
            return false;
        }
    }

    /**
     * Insert instance
     * @param authors authors
     * @param year year of book
     * @param bookname name of book
     * @param publish publish
     * @param cost book cost
     * @return true if everything is ok, else false
     */
    @Override
    public boolean insertInstance(List<Author> authors, Integer year, String bookname, String publish, Integer cost) {
        logger.debug("insertion instance");

        logger.debug("insertion book");
        Connections.getFactory().getBookDao().insertBook(new Book(0, bookname));
        int id_b = Connections.getFactory().getBookDao().findBook(new Book(0, bookname));
        if (id_b == -1) {
            logger.error("Couldn't insert new Book");
            return false;
        }

        for (Author author:authors) {
            //insert author
            Connections.getFactory().getAuthorDao().insertAuthor(author);
            int id_a = Connections.getFactory().getAuthorDao().findAuthor(author);
            if (id_a == -1) {
                logger.error("Couldn't insert new Author");
                return false;
            }
            logger.debug("Insert Author-Book communication");
            try(final Connection connection = OracleDAOFactory.getConnection();
                final Statement statement = connection.createStatement()){

                statement.executeUpdate(
                        "insert into AuBook (author_id, book_id) " +
                                "values (" + id_a + "," + id_b + ")" );

            } catch (SQLException e) {
                logger.error("SQLException insertInstance", e);
                return false;
            }
        }


        logger.debug("Insert Instance");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into Instance (id_book, year_b, publish, cost, status, comments) " +
                            "values (" + id_b + ","+ year + ", '" +
                            publish +"' ," + cost + ", " +
                            1 + ", '')" ) >0;

        } catch (SQLException e) {
            logger.debug("SQLExcetion in insertion Instance",e);
            return false;
        }


    }

}
