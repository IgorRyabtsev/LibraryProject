package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.Oracle.condition.OracleAuthorCondition;
import main.java.controllers.DAO.Oracle.condition.OracleBookCondition;
import main.java.controllers.DAO.condition.AuthorCondition;
import main.java.controllers.DAO.condition.BookCondition;
import main.java.controllers.DAO.interfaces.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by igor on 21.04.16.
 */
public class OracleDAOFactory implements DAOFactory {

    final private static String driverName = "oracle.jdbc.driver.OracleDriver";
    final private static String username = "igorEpam";
    final private static String password = "fyfyfc777";
    final private static String url= "jdbc:oracle:thin:@localhost:1521:XE";

    public static Connection getConnection() throws SQLException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(driverName).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    @Override
    public ReaderDao getReaderDao() { return new OracleReaderDao(); }

    @Override
    public AuthorDao getAuthorDao() { return new OracleAuthorDao(); }

    @Override
    public AuthorCondition getAuthorConditionDao() { return new OracleAuthorCondition(); }

    @Override
    public BookDao getBookDao() { return new OracleBookDao(); }

    @Override
    public InstanceDao getInstanceDao() { return new OracleInstanceDao(); }

    @Override
    public OrdersDao getOrdersDao() { return new OracleOrdersDao(); }

    @Override
    public ReaderOrdersDao getReaderOrdersDao() { return new OracleReaderOrdersDao(); }

}
