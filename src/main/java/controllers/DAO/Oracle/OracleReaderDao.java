package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.interfaces.ReaderDao;
import main.java.controllers.model.Reader;
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
public class OracleReaderDao implements ReaderDao {

    private static final Logger logger = Logger.getLogger(OracleReaderDao.class);
    @Override
    public Reader getReaderByEmail(String email) {
        logger.debug("getReaderByEmail");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_r, namer_f, namer_s, namer_p, year, email, password, role FROM Reader where email = '" + email +"'")) {
            if (rs.next()) {
                return new Reader(rs.getInt("id_r"),
                        rs.getString("namer_f"),
                        rs.getString("namer_s"),
                        rs.getString("namer_p"),
                        rs.getInt("year"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"));
            }

        } catch (SQLException e) {
            logger.error("SQLException in getting Reader by email",e);
        }
        return null;
    }

    @Override
    public Reader getReaderById(int id) {
        logger.debug("getReaderById");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                     "SELECT id_r, namer_f, namer_s, namer_p, year, email, password, role FROM Reader where id_r = " + id)) {
            if (rs.next()) {
                return new Reader(rs.getInt("id_r"),
                                    rs.getString("namer_f"),
                                    rs.getString("namer_s"),
                                    rs.getString("namer_p"),
                                    rs.getInt("year"),
                                    rs.getString("email"),
                                    rs.getString("password"),
                                    rs.getString("role"));
            }

        } catch (SQLException e) {
            logger.error("SQLException in getting Reader by id",e);
        }
        return null;
    }

    @Override
    public boolean insertReader(Reader r) {
        if (r == null) {
            return false;
        }
        logger.debug("Insert new reader");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into Reader (namer_f, namer_s, namer_p, year, email, password, role) " +
                            "values (" + parseforInsert(r) + ")") > 0;

        } catch (SQLException e) {
            logger.error("SQLException in insertion new Reader",e);
            return false;
        }
    }

    private String parseforInsert(Reader r) {
        StringBuffer tmp = new StringBuffer();
        tmp.append("'" + r.getNamer_f()+"'" + ", ");
        tmp.append("'" + r.getNamer_s()+"'" + ", ");
        tmp.append("'" + r.getNamer_p()+"'" + ", ");
        tmp.append(r.getYear() + ", ");
        tmp.append("'" + r.getEmail()+"'" + ", ");
        tmp.append("'" + r.getPassword()+"'" + ", ");
        tmp.append("'" + r.getRole()+"'");
        return tmp.toString();
    }

    @Override
    public List<Reader> getAll() {
        List<Reader> readers = new ArrayList<>();
        logger.debug("getAll");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_r, namer_f, namer_s, namer_p, year, email, password, role FROM Reader where role!='librarian'")) {
            while (rs.next()) {
                readers.add(parseResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("SQLException in getAll",e);
        }
        return readers;
    }

    @Override
    public boolean makeLibrarian(int id) {
        logger.debug("Updating role");
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){
            return statement.executeUpdate(
                    "Update Reader set role='librarian' where id_r=" + id) > 0;
        } catch (SQLException e) {
            logger.error("SQLException in makeLibrarian",e);
            return false;
        }
    }

    private Reader parseResultSet(ResultSet rs) throws SQLException {
        return new Reader(rs.getInt("id_r"),
                rs.getString("namer_f"),
                rs.getString("namer_s"),
                rs.getString("namer_p"),
                rs.getInt("year"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role"));
    }
}
