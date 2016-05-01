package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.condition.BookCondition;
import main.java.controllers.DAO.interfaces.BookDao;
import main.java.controllers.model.Book;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igor on 21.04.16.
 */

public class OracleBookDao implements BookDao {

    private static final Logger logger = Logger.getLogger(OracleBookDao.class);
    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        logger.debug("getAll method: SELECT id_a, name_f, name_s, name_p, year_a FROM Author");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_b, name_b FROM Book")) {
            while (rs.next()) {
                books.add(parseResultSet(rs));
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            logger.error("SQLException getAll",e);
        }
        return books;
    }

    private Book parseResultSet(ResultSet rs) throws SQLException {
        Book book = new Book(rs.getInt("id_b"),
                rs.getString("name_b"));
        return book;
    }

    @Override
    public boolean insertBook(Book book) {
        if (book == null) {
            return false;
        }
        logger.debug("insertBook");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into Book (name_b) " +
                            "values (" + parseforInsert(book) + ")") > 0;

        } catch (SQLException e) {
            logger.debug("SQLException insertBook method",e);
        }
        return false;
    }

    private String parseforInsert(Book book) {
        StringBuffer tmp = new StringBuffer();
        tmp.append("'" + book.getName_b()+"'");
        return tmp.toString();
    }

    @Override
    public int findBook(Book book) {
        logger.debug("findBook");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_b FROM Book where name_b = '" + book.getName_b() + "' order by id_b desc")) {
            if (rs.next()) {
                return rs.getInt("id_b");
            }
        } catch (SQLException e) {
            logger.error("SQLException findBook",e);
        }
        return -1;
    }
}
