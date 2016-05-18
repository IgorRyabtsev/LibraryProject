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

/**
 * Class for working with DB table Orders
 * @author igor
 */

public class OracleOrdersDao implements OrdersDao {

    private static final Logger logger = Logger.getLogger(OracleOrdersDao.class);

    /**
     * Get list of orders
     * @return list of orders
     */
    @Override
    public List<Orders> getAllOrders() {
        return getOrdersByEmail(null,false);
    }

    /**
     * Get order by email
     * @param email reader email
     * @param isRetDateNull if we need return date == null, then =true, else false
     * @return list of orders
     */
    @Override
    public List<Orders> getOrdersByEmail(String email, boolean isRetDateNull) {
        List<Orders> orders = new ArrayList<>();
        String additional = "and email = '"+email+"'";
        if(email==null) {
            additional = "";
        }
        String nullableRetDate=" and return_date is null ";
        if(!isRetDateNull) {
            nullableRetDate = "";
        }
        logger.debug("Get list of orders");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_r, namer_f, namer_s, namer_p, year, email, password, role," +
                            "id_i, id_book, name_b, year_b, publish, cost, status, comments," +
                            "id_o, release_date, return_date FROM ((Orders join Instance on id_i=instance_id) " +
                            "join Reader on reader_id=id_r) join Book on id_b=id_book where 1=1 "
                            + additional + nullableRetDate)) {
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

    /**
     * Give book to reader
     * @param id_r reader id
     * @param date release date
     * @param instance instance
     * @return true if everything is ok, else false
     */
    @Override
    public boolean giveBook(int id_r, Date date, Instance instance) {
        int exec;
        PreparedStatement pstmt = null;
        logger.debug("Insert into Orders");
        try(final Connection connection = OracleDAOFactory.getConnection()){

            pstmt = connection.prepareStatement("Insert into Orders (reader_id, instance_id, release_date," +
                    " return_date) VALUES (?,?,?,null)");
            pstmt.setInt(1,id_r);
            pstmt.setInt(2,instance.getId_i());
            pstmt.setDate(3,date);

            exec = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQLException insertion into Orders",e);
            return false;
        } finally {
            try {
                if(pstmt!=null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                logger.error("SQLException giveBook can't close Prepared Statement ",e);
            }
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
        if(!b) {
            return false;
        }
        logger.debug("delete order from OrdNum");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "delete from OrdNum where numreader=" + id_r + " and booko_id=" + instance.getBook().getId_b() +
                            " and publish_o='" + instance.getPublish()+"' and rownum=1") > 0;

        } catch (SQLException e) {
            logger.error("SQLException in delete order from OrdNum",e);
            return false;
        }
    }

    /**
     * Return release date by order id
     * @param id order id
     * @return release date if something goes wrong return null
     */
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

    /**
     * Take book from reader
     * @param id order id
     * @param date returning date
     * @param comments comments by book
     * @return true if taking is ok, else false
     */
    @Override
    public boolean takeBook(int id, Date date, String comments){
        int exec;
        PreparedStatement pstmt = null;
        logger.debug("update Orders");
        try(final Connection connection = OracleDAOFactory.getConnection()){
            pstmt = connection.prepareStatement("update Orders set return_date = ? " +
                    "where id_o=?" );
            pstmt.setDate(1,date);
            pstmt.setString(2,String.valueOf(id));
            exec = pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("SQLException in updating Orders");
            return false;
        } finally {
            try {
                if(pstmt!=null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                logger.error("SQLException in takeBook can't close Prepared Statement  ",e);
            }
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

    /**
     * Get order by id
     * @param id order id
     * @return {@link Orders}, if no such order or if something goes wrong return null
     */
    @Override
    public Orders getOrderById(int id) {
        logger.debug("Get order id");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "select id_r, namer_f, namer_s, namer_p, year, email, password, role," +
                            " id_i, id_book, name_b, year_b, publish, cost, status, comments," +
                            " id_o, release_date, return_date from (((Orders join Instance on id_i=instance_id)" +
                            " join Reader on reader_id=id_r) join Book on id_b=id_book) where id_o="+id)) {
            while (rs.next()) {
                return new Orders(rs.getInt("id_o"),
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
                        rs.getDate("return_date"));

            }
        } catch (SQLException e) {
            logger.error("SQLException getOrderById",e);
            return null;
        }
        return null;
    }

    /**
     * Delete order by id
     * @param id order id
     * @return true if everything is ok, else false
     */
    @Override
    public boolean deleteById(int id) {
        logger.debug("delete order");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "delete from Orders where id_o=" + id) > 0;

        } catch (SQLException e) {
            logger.error("SQLException in delete order from OrdNum",e);
            return false;
        }
    }

    /**
     * Set release and return date
     * @param id order id
     * @param rel_date release date
     * @param ret_date return date
     * @return true if everything is ok, else false
     */
    @Override
    public boolean setDate(int id, Date rel_date, Date ret_date){
        logger.debug("update Orders");
        try(final Connection connection = OracleDAOFactory.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement("update Orders set release_date=?, return_date = ? " +
                    "where id_o=?" );
            pstmt.setDate(1,rel_date);
            pstmt.setDate(2,ret_date);
            pstmt.setInt(3,id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("SQLException in updating Orders");
            return false;
        }
    }

}