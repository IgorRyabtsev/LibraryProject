package main.java.controllers.DAO.Oracle;


import main.java.controllers.DAO.Connections;
import main.java.controllers.DAO.interfaces.ReaderOrdersDao;
import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Reader;

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
public class OracleReaderOrdersDao implements ReaderOrdersDao {

    @Override
    public List<Instance> getListOfOrdersByEmail(String email) {
        List<Instance> inst = new ArrayList<>();
        Map<Integer,Integer> bookCount = new HashMap<>(); // <book_id, count of instances>
        try (final Connection connection = OracleDAOFactory.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet rs = statement.executeQuery(
                     "select count(booko_id) as cnt, booko_id from ORDNUM " +
                             "where numreader in (select id_r from Reader where email = '" + email + "') group by booko_id")) {
            while (rs.next()) {
                bookCount.put(rs.getInt("booko_id"),rs.getInt("cnt"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (bookCount.isEmpty()) return null;

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
                throw new RuntimeException(e);
            }
        }
        return inst;
    }

    @Override
    public boolean insertOrder(Reader reader, Instance instance) {
        if (reader == null || instance == null) return false;
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into OrdNum (numreader, booko_id, publish_o) " +
                            "values (" + reader.getId_r() +", " + instance.getBook().getId_b() + ", '" + instance.getPublish() + "')") > 0 ? true : false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map.Entry<Instance, List<Author>>> getInstancesByReader(Reader r) {
        List<Map.Entry<Instance, List<Author>>> instances = new ArrayList<>();
        List<Instance> listOfOrders = getListOfOrdersByEmail(r.getEmail());
        for (Instance inst:listOfOrders) {
            instances.add(Connections.getFactory().getInstanceDao().getInstanceById(inst.getId_i()));
        }
        return instances;
    }

    @Override
    public boolean deleteOrderByReader(Reader reader, Book book, String publish){
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "Delete from Ordnum where numreader = " + reader.getId_r() + " " +
                            "and booko_id=" + book.getId_b() + " and publish_o='" + publish +
                            "' and rownum=1") > 0 ? true : false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        return false;
    }

}
