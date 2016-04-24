package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.interfaces.OrdersDao;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Orders;
import main.java.controllers.model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 22.04.16.
 */
public class OracleOrdersDao implements OrdersDao {

    @Override
    public List<Orders> getAllOrders() {
        return getOrdersByEmail(null);
    }

    @Override
    public List<Orders> getOrdersByEmail(String email) {
        List<Orders> orders = new ArrayList<>();
        String additional = "where email = '"+email+"'";
        if(email==null) {
            additional = "";
        }
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_r, namer_f, namer_s, namer_p, year, email, password, role," +
                            "id_i, id_book, name_b, year_b, publish, cost, status, comments," +
                            "id_o, release_date, return_date FROM ((Orders join Instance on id_i=instance_id) " +
                            "join Reader on reader_id=id_r) join Book on id_b=id_book "
                            + additional)) {
            while (rs.next()) {
                orders.add(new Orders(rs.getInt("id_o"),
                        new Reader(rs.getInt("id_r"),
                                rs.getString("namer_f"),
                                rs.getString("namer_s"),
                                rs.getString("namer_p"),
                                rs.getInt("year"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("role")),
                        new Instance(rs.getInt("id_i"),
                                new Book(rs.getInt("id_book"),
                                        rs.getString("name_b")),
                                rs.getInt("year_b"),
                                rs.getString("publish"),
                                rs.getInt("cost"),
                                rs.getInt("status"),
                                rs.getString("comments")),
                        rs.getDate("release_date"),
                        rs.getDate("return_date")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override //TODO: мб тут сделать по email
    public boolean takeBook(String email, Date date, Instance instance) {
        int exec;
//        System.out.println(date);
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            PreparedStatement pstmt = connection.prepareStatement("update Orders set return_date = ? " +
                    "where (reader_id in (select id_r from Reader where email = ?) and instance_id in \n" +
                    "(select id_i from Instance where id_book=24 and status=0  and year_b = 2015 \n" +
                    "and publish = ?))");
            pstmt.setDate(1,date);
            pstmt.setString(2,email);
            pstmt.setString(3,instance.getPublish());

            exec = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(exec<1) {
            return false;
        }
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "update Instance set status = " + 1 + " where id_i in " +
                            "(select id_i from Instance where id_book=" + instance.getBook().getId_b() + " and status=0  " +
                            "and year_b = " + instance.getYear_b() + " and publish = '" + instance.getPublish() + "' "
                            + ")") > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override//TODO: выдача книг хз еще как будет
    public boolean giveBook(String email, Date date) {
        return false;
    }
    //TODO: вывести список заказов



}
