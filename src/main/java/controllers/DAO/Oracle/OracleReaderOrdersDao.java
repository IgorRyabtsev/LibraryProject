package main.java.controllers.DAO.Oracle;


import main.java.controllers.DAO.interfaces.ReaderOrdersDao;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;

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
}
