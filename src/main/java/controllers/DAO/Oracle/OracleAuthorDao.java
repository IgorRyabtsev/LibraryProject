package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.Connections;
import main.java.controllers.DAO.condition.AuthorCondition;
import main.java.controllers.DAO.interfaces.AuthorDao;
import main.java.controllers.model.Author;
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

/**
 * Class for working with DB table Author
 * @author igor
 */

public class OracleAuthorDao implements AuthorDao {

    private static final Logger logger = Logger.getLogger(OracleAuthorDao.class);

    /**
     * Get list of {@link Author}
     * @return list of authors
     */
    @Override
    public List<Author> getAll() {
        List<Author> auths = new ArrayList<>();
        logger.debug("getAll method: SELECT id_a, name_f, name_s, name_p, year_a FROM Author");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a FROM Author")) {
            while (rs.next()) {
                auths.add(parseResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("SQLException getAll", e);
        }
        return auths;
    }

    /**
     * Parse ResultSet to take Author
     * @param rs ResultSet
     * @return author
     * @throws SQLException
     */
    private Author parseResultSet(ResultSet rs) throws SQLException{
        Author r = new Author(rs.getInt("id_a"),
                rs.getString("name_f"),
                rs.getString("name_s"),
                rs.getString("name_p"),
                rs.getInt("year_a"));
        return r;
    }

    /**
     * Insert new author
     * @param au auhtor
     * @return true if everything is ok, else false
     */
    @Override
    public boolean insertAuthor(Author au) {
        if (au == null) {
            return false;
        }
        AuthorCondition authorCondition = Connections.getFactory().getAuthorConditionDao();
        authorCondition.setName_f(au.getName_f());
        authorCondition.setName_s(au.getName_s());
        authorCondition.setName_p(au.getName_p());
        authorCondition.setYear_a(String.valueOf(au.getYear_a()));
        if(findByCondition(authorCondition)) {
            return false;
        }
        logger.debug("Insertion of new Author");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into Author (name_f, name_s, name_p, year_a) " +
                            "values (" + parseforInsert(au) + ")") > 0;

        } catch (SQLException e) {
            logger.error("SQLException insertAuthor", e);
        }
        return false;
    }

    /**
     * Parse Author for inserting into Author
     * @param au author
     * @return part of sql expression for insertion
     */
    private String parseforInsert(Author au) {
        StringBuffer tmp = new StringBuffer();
        tmp.append("'" + au.getName_f()+"'" + ", ");
        tmp.append("'" + au.getName_s()+"'" + ", ");
        tmp.append("'" + au.getName_p()+"'" + ", ");
        tmp.append(au.getYear_a());
        return tmp.toString();
    }

    /**
     * Find Author by condition
     * @param authorCondition condition
     * @return true if exists, else false
     */
    private boolean findByCondition(AuthorCondition authorCondition) {
        logger.debug("findByCondition");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a FROM Author where " + authorCondition.getExpression() )) {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("SQLException findByCondition", e);
        }
        return false;
    }

    /**
     * Find author
     * @param author author
     * @return autho id in Author table
     */
    @Override
    public int findAuthor(Author author) {
        logger.debug("findAuthor");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a FROM Author where name_f = '" + author.getName_f() + "' and name_s = '" + author.getName_s() +
                            "' and name_p = '" + author.getName_p() +"' and year_a = " + author.getYear_a())) {
            if (rs.next()) {
                return rs.getInt("id_a");
            }
        } catch (SQLException e) {
            logger.error("SQLException findAuthor", e);
        }
        return -1;
    }

}
