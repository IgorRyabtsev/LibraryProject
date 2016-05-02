package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.interfaces.OrdersDao;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;
import main.java.controllers.model.Orders;
import main.java.controllers.model.Reader;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 22.04.16.
 */
public class OracleOrdersDao implements OrdersDao {

    private static final Logger logger = Logger.getLogger(OracleOrdersDao.class);
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
        logger.debug("Get list of orders");
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
            logger.error("SQLException getOrdersByEmail",e);
        }
        return orders;
    }

    @Override
    public boolean giveBook(int id_r, Date date, Instance instance) {
        int exec;
        logger.debug("Insert into Orders");
        try(final Connection connection = OracleDAOFactory.getConnection()){

            final PreparedStatement pstmt = connection.prepareStatement("Insert into Orders (reader_id, instance_id, release_date," +
                    " return_date) VALUES (?,?,?,null)");
            pstmt.setInt(1,id_r);
            pstmt.setInt(2,instance.getId_i());
            pstmt.setDate(3,date);

            exec = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQLException insertion into Orders",e);
            return false;
        }

        if(exec<1) {
            return false;
        }

        boolean b;
        logger.debug("update instance, setting status=0");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){
            b = statement.executeUpdate(
                    "update Instance set status = " + 0 + " where id_i=" + instance.getId_i()) > 0;
        } catch (SQLException e) {
            logger.error("SQLException in updating \"status\"",e);
            return false;
        }
        if(b==false) {
            return false;
        }
        logger.debug("delete order from OrdNum");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "delete from OrdNum where numreader=" + id_r + " and booko_id=" + instance.getBook().getId_b() + " and publish_o='" + instance.getPublish()+"' and rownum=1") > 0;

        } catch (SQLException e) {
            logger.error("SQLException in delete order from OrdNum",e);
            return false;
        }
    }

    @Override
    public Date getReleaseDateById(int id) {
        logger.debug("Get date by id");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "select release_date from orders where id_o="+id)) {
            while (rs.next()) {
                return rs.getDate("release_date");
            }
        } catch (SQLException e) {
            logger.error("SQLException getReleaseDateById",e);
            return null;
        }
        return null;
    }

    @Override
    public boolean takeBook(int id, Date date, String comments){
        int exec;
        logger.debug("update Orders");
        try(final Connection connection = OracleDAOFactory.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement("update Orders set return_date = ? " +
                    "where id_o=?" );
            pstmt.setDate(1,date);
            pstmt.setString(2,String.valueOf(id));
            exec = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQLException in updating Orders");
            return false;
        }
        if(exec<1) {
            return false;
        }

        logger.debug("Updating Instance, setting \"status=1\"");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){
            return statement.executeUpdate(
                    "update Instance set status = " + 1 + ", comments='" + comments + "' where id_i in (select instance_id from " +
                            " orders where id_o="+id+")") > 0;

        } catch (SQLException e) {
            logger.error("SQLException in updating Instance in takeBook",e);
            return false;
        }
    }


}