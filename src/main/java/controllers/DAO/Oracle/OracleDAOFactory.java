package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.Oracle.condition.OracleAuthorCondition;
import main.java.controllers.DAO.Oracle.condition.OracleBookCondition;
import main.java.controllers.DAO.condition.AuthorCondition;
import main.java.controllers.DAO.condition.BookCondition;
import main.java.controllers.DAO.interfaces.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Class for connecting to DB
 * @author igor
 */
public class OracleDAOFactory implements DAOFactory {

    final private static String driverName = "oracle.jdbc.driver.OracleDriver";
    final private static String username = "igorEpam";
    final private static String password = "fyfyfc777";
    final private static String url= "jdbc:oracle:thin:@localhost:1521:XE";
    final private static Logger logger = Logger.getLogger(OracleDAOFactory.class);

    /**
     * Return connection by params
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(driverName).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            logger.error("Exception in getConnection",e);
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    /**
     * Return reader dao
     * @return {@link OracleReaderDao}
     */
    @Override
    public ReaderDao getReaderDao() { return new OracleReaderDao(); }

    /**
     * Return author dao
     * @return {@link OracleAuthorDao}
     */
    @Override
    public AuthorDao getAuthorDao() { return new OracleAuthorDao(); }

    /**
     * Return author condition
     * @return {@link OracleAuthorCondition}
     */
    @Override
    public AuthorCondition getAuthorConditionDao() { return new OracleAuthorCondition(); }

    /**
     * Return book dao
     * @return {@link OracleBookDao}
     */
    @Override
    public BookDao getBookDao() { return new OracleBookDao(); }

    /**
     * Return instance dao
     * @return {@link OracleInstanceDao}
     */
    @Override
    public InstanceDao getInstanceDao() { return new OracleInstanceDao(); }

    /**
     * Return orders dao
     * @return {@link OracleOrdersDao}
     */
    @Override
    public OrdersDao getOrdersDao() { return new OracleOrdersDao(); }

    /**
     * Return reader orders dao dao
     * @return {@link OracleReaderDao}
     */
    @Override
    public ReaderOrdersDao getReaderOrdersDao() { return new OracleReaderOrdersDao(); }

}
