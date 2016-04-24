package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.interfaces.ReaderDao;
import main.java.controllers.model.Reader;

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

    @Override
    public Reader getReaderByEmail(String email) {
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
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Reader getReaderById(int id) {
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
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insertReader(Reader r) {
        if (r == null) return false;
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into Reader (namer_f, namer_s, namer_p, year, email, password, role) " +
                            "values (" + parseforInsert(r) + ")") > 0 ? true : false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //когда добавишь logger пиши тут return false
    }

    @Override
    public boolean deleteReaderById(int id) {

        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "Delete from Reader where id_r = " + id ) > 0 ? true : false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public boolean findByEmail(String email) {
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_r, namer_f, namer_s, namer_p, year, email, password, role FROM Reader where email = '" + email + "'")) {
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public List<Reader> getAll() {
        List<Reader> readers = new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_r, namer_f, namer_s, namer_p, year, email, password, role FROM Reader")) {
            while (rs.next()) {
                readers.add(parseResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return readers;
    }

    private Reader parseResultSet(ResultSet rs) throws SQLException {
        Reader r = new Reader(rs.getInt("id_r"),
                            rs.getString("namer_f"),
                            rs.getString("namer_s"),
                            rs.getString("namer_p"),
                            rs.getInt("year"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"));

        return r;
    }


}
