package main.java.controllers.DAO.Oracle;


import main.java.controllers.DAO.Connections;
import main.java.controllers.DAO.interfaces.ReaderOrdersDao;
import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Reader;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 22.04.16.
 */

/**
 * Class for working with DB table OrdNum
 * @author igor
 */

public class OracleReaderOrdersDao implements ReaderOrdersDao {

    private static final Logger logger = Logger.getLogger(OracleReaderOrdersDao.class);

    /**
     * Return list of {@link Instance} - not given instances by email
     * @param email reader email
     * @return list og Instances
     */
    @Override
    public List<Instance> getListOfOrdersByEmail(String email) {
        List<Instance> inst = new ArrayList<>();
        Map<Integer,Integer> bookCount = new HashMap<>(); // <book_id, count of instances>
        logger.debug("getting books, and count of books");
        try (final Connection connection = OracleDAOFactory.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet rs = statement.executeQuery(
                     "select count(booko_id) as cnt, booko_id from ORDNUM " +
                             "where numreader in (select id_r from Reader where email = '" + email + "') group by booko_id")) {
            while (rs.next()) {
                bookCount.put(rs.getInt("booko_id"),rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            logger.error("SQLException in getting books, and count of books", e);
            return null;
        }

        if (bookCount.isEmpty()) {
            return null;
        }

        logger.debug("get List of instances");
        for (Map.Entry entry: bookCount.entrySet()) {
            int count = (Integer) entry.getValue();
            int id_book = (Integer) entry.getKey();
            try (final Connection connection = OracleDAOFactory.getConnection();
                 final Statement statement = connection.createStatement();
                 final ResultSet rs = statement.executeQuery(
                         "SELECT distinct id_i, id_book, year_b, publish, cost, status, comments, name_b " +
                                 "FROM (((Instance join Book on " +
                                 "id_book=id_b) join OrdNum on booko_id=id_book)) join Reader " +
                                 "on id_r=numreader where email ='" + email + "' and publish = publish_o and status=1 " +
                                 "and id_book=" + id_book)) {
                while (rs.next() && count>0) {
                    count--;
                    inst.add(new Instance(rs.getInt("id_i"),
                        new Book(rs.getInt("id_book"), rs.getString("name_b")),
                        rs.getInt("year_b"),
                        rs.getString("publish"),
                        rs.getInt("cost"),
                        rs.getInt("status"),
                        rs.getString("comments")));
                }
            } catch (SQLException e) {
                logger.error("SQLException in getting List of instances",e);
            }
        }
        return inst;
    }

    /**
     * Insert new order into OrdNum table
     * @param reader {@link Reader}
     * @param instance {@link Instance}
     * @return true if insertion is ok, else false
     */
    @Override
    public boolean insertOrder(Reader reader, Instance instance) {
        if (reader == null || instance == null) {
            return false;
        }

        int countInLibrary=0;
        try (final Connection connection = OracleDAOFactory.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet rs = statement.executeQuery(
                     "select count(id_book) as cnt from Instance " +
                             "where id_book in (select b.id_b from Book b where b.name_b='" + instance.getBook().getName_b()
                             + "') and publish='" + instance.getPublish()
                             + "' group by id_book")) {
            if (rs.next()) {
                countInLibrary=rs.getInt("cnt");
            }
        } catch (SQLException e) {
            logger.error("SQLException in counting instances", e);
        }

        int countInOrder=0;
        try (final Connection connection = OracleDAOFactory.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet rs = statement.executeQuery(
                     "select count(booko_id) as cnt from OrdNum " +
                             "where booko_id in (select b.id_b from Book b where b.name_b='" + instance.getBook().getName_b()
                             + "') and publish_o='" + instance.getPublish()
                             + "' and numreader=" + reader.getId_r() + " group by booko_id")) {
            if (rs.next()) {
                countInOrder=rs.getInt("cnt");
            }
        } catch (SQLException e) {
            logger.error("SQLException in countung instances in OrdNum", e);
        }


        if(countInOrder>=countInLibrary) {
            logger.debug("not inserted new line, because of count limit");
            return true;
        }

        logger.debug("insertOrder");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into OrdNum (numreader, booko_id, publish_o) " +
                            "values (" + reader.getId_r() + ", " + instance.getBook().getId_b() +
                            ", '" + instance.getPublish() + "')") > 0;

        } catch (SQLException e) {
            logger.error("SQLException in insertion new Order", e);
            return false;
        }
    }

    /**
     * Return list of instances, that orders {@link Reader}
     * @param r {@link Reader}
     * @return list of instances
     */
    @Override
    public List<Map.Entry<Instance, List<Author>>> getInstancesByReader(Reader r) {
        List<Map.Entry<Instance, List<Author>>> instances = new ArrayList<>();
        List<Instance> listOfOrders = getListOfOrdersByEmail(r.getEmail());
        if(listOfOrders == null) {
            return new ArrayList<>();
        }
        for (Instance inst:listOfOrders) {
            instances.add(Connections.getFactory().getInstanceDao().getInstanceById(inst.getId_i()));
        }
        return instances;
    }

    /**
     * Get list of orders by email
     * @param email reader email
     * @return list of instances
     */
    @Override
    public List<Instance> getListOfOrdersByEmailForLibrarian(String email) {
        List<Instance> inst = new ArrayList<>();
        Map<Integer,Integer> bookCount = new HashMap<>(); // <book_id, count of instances>
        logger.debug("get book_id-count");
        try (final Connection connection = OracleDAOFactory.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet rs = statement.executeQuery(
                     "select count(booko_id) as cnt, booko_id from ORDNUM " +
                             "where numreader in (select id_r from Reader where email = '" + email + "') group by booko_id")) {
            while (rs.next()) {
                bookCount.put(rs.getInt("booko_id"),rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            logger.error("SQLException in getting book_id-count", e);
        }

        if (bookCount.isEmpty()) {
            return new ArrayList<>(); // NULL было
        }


        logger.debug("get List of instances as Orders");
        for (Map.Entry entry: bookCount.entrySet()) {
            int count = (Integer) entry.getValue();
            int id_book = (Integer) entry.getKey();
            try (final Connection connection = OracleDAOFactory.getConnection();
                 final Statement statement = connection.createStatement();
                 final ResultSet rs = statement.executeQuery(
                         "SELECT distinct id_i, id_book, year_b, publish, cost, status, comments, name_b " +
                                 "FROM (((Instance join Book on " +
                                 "id_book=id_b) join OrdNum on booko_id=id_book)) join Reader " +
                                 "on id_r=numreader where email ='" + email + "' and publish = publish_o " +
                                 "and id_book=" + id_book)) {
                while (rs.next() && count>0) {
                    count--;
                    inst.add(new Instance(rs.getInt("id_i"),
                            new Book(rs.getInt("id_book"), rs.getString("name_b")),
                            rs.getInt("year_b"),
                            rs.getString("publish"),
                            rs.getInt("cost"),
                            rs.getInt("status"),
                            rs.getString("comments")));
                }
            } catch (SQLException e) {
                logger.error("SQLException in getting list of Orders", e);
            }
        }
        return inst;
    }

    /**
     * Get list of instances by reader from OrdNum table
     * @param r {@link Reader}
     * @return list of instances
     */
    @Override
    public List<Map.Entry<Instance, List<Author>>> getInstancesByReaderForLibrarian(Reader r) {
        List<Map.Entry<Instance, List<Author>>> instances = new ArrayList<>();
        List<Instance> listOfOrders = getListOfOrdersByEmailForLibrarian(r.getEmail());
        if(listOfOrders==null ) return new ArrayList<>();;
        for (Instance inst:listOfOrders) {
            instances.add(Connections.getFactory().getInstanceDao().getInstanceById(inst.getId_i()));
        }
        return instances;
    }

    /**
     * Delete order from OrdNum table
     * @param reader {@link Reader}
     * @param book {@link Book}
     * @param publish publish of book
     * @return true if deleting is ok, else false
     */
    @Override
    public boolean deleteOrderByReader(Reader reader, Book book, String publish){
        logger.debug("Delete Order");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "Delete from Ordnum where numreader = " + reader.getId_r() + " " +
                            "and booko_id=" + book.getId_b() + " and publish_o='" + publish +
                            "' and rownum=1") > 0;

        } catch (SQLException e) {
            logger.error("SQLException in deleting Order");
            return false;
        }
    }

}
