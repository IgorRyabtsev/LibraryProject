package main.java.controllers.DAO.Oracle;

import main.java.controllers.DAO.Connections;
import main.java.controllers.DAO.interfaces.InstanceDao;
import main.java.controllers.model.AuthBook;
import main.java.controllers.model.Author;
import main.java.controllers.model.Book;
import main.java.controllers.model.Instance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by igor on 21.04.16.
 */
public class OracleInstanceDao implements InstanceDao {

    @Override
    public Map.Entry<Instance,List<Author>> getInstanceById(int id) {
        Instance instance = new Instance();
        List<Author> authors = new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_i, id_book, year_b, publish, cost, status, comments, name_b FROM Instance join Book on " +
                            "id_book=id_b where id_i=" + id)) {
            while (rs.next()) {
                instance = new Instance(rs.getInt("id_i"),
                                new Book(rs.getInt("id_book"),rs.getString("name_b")),
                                rs.getInt("year_b"),
                                rs.getString("publish"),
                                rs.getInt("cost"),
                                rs.getInt("status"),
                                rs.getString("comments"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(instance.getPublish()==null) return null;

        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT distinct id_a, name_f, name_s, name_p, year_a  FROM " +
                            "((Instance join AuBook on id_book=book_id) join Author on id_a=author_id) " +
                            "where id_book = " + instance.getBook().getId_b())) {
            while (rs.next()) {
                authors.add(new Author(rs.getInt("id_a"),
                        rs.getString("name_f"),
                        rs.getString("name_s"),
                        rs.getString("name_p"),
                        rs.getInt("year_a")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(authors.isEmpty()) return null;

        Map.Entry<Instance,List<Author>> instAuth = new AbstractMap.SimpleEntry<>(instance, authors);
        return instAuth;
    }

    @Override
    public List<Map<Instance, List<Author>>> getAll() {
        return getInstanceByNameV2(null,0);
    }

//    // second parameter 0 want to see all instanses, 1 instances, that present
//    @Override
//    public List<Map<Instance, List<Author>>> getInstanceByName(String name, int status) {
//        String additional;
//        if(name == null) {
//            additional="";
//        } else {
//            additional = "where name_b = '"+ name +"' and status  " + ((status==1) ? "=1" : ">=0");
//        }
//        List<Map<Instance, List<Author>>> instances = new ArrayList<>();
//
//        List<Instance> inst = new ArrayList<>();
//        try(final Connection connection = OracleDAOFactory.getConnection();
//            final Statement statement = connection.createStatement();
//            final ResultSet rs = statement.executeQuery(
//                    "SELECT id_i, id_book, year_b, publish, cost, status, comments, name_b FROM Instance join Book on " +
//                            "id_book=id_b " + additional)) {
//            while (rs.next()) {
//                inst.add(new Instance(rs.getInt("id_i"),
//                        new Book(rs.getInt("id_book"),rs.getString("name_b")),
//                        rs.getInt("year_b"),
//                        rs.getString("publish"),
//                        rs.getInt("cost"),
//                        rs.getInt("status"),
//                        rs.getString("comments")));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        //if no instances
//        if(inst.isEmpty()) return new ArrayList<>();;
//
//        Map<Instance, List<Author>> mapinst;
//        List<Author> auth;
//        for (Instance instance: inst) {
//            mapinst = new HashMap<>();
//            auth = new ArrayList<>();
//
//            try(final Connection connection = OracleDAOFactory.getConnection();
//                final Statement statement = connection.createStatement();
//                final ResultSet rs = statement.executeQuery(
//                        "SELECT distinct id_a, name_f, name_s, name_p, year_a  FROM ((Instance join AuBook on id_book=book_id) join Author on id_a=author_id) " +
//                                "where id_book = " + instance.getBook().getId_b())) {
//                while (rs.next()) {
//                    auth.add(new Author(rs.getInt("id_a"),
//                            rs.getString("name_f"),
//                            rs.getString("name_s"),
//                            rs.getString("name_p"),
//                            rs.getInt("year_a")));
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            mapinst.put(instance,auth);
//            instances.add(mapinst);
//        }
//        return instances;
//    }

    @Override
    public List<Map<Instance, List<Author>>> getInstanceByNameV2(String name, int status) {
        String additional;
        if(name == null) {
            additional="";
        } else {
            additional = "where name_b = '"+ name +"' and status  " + ((status==1) ? "=1" : ">=0");
        }
        List<Map<Instance, List<Author>>> instances = new ArrayList<>();

        List<Instance> inst = new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_i, id_book, year_b, publish, cost, status, comments, name_b FROM Instance join Book on " +
                            "id_book=id_b " + additional)) {
            while (rs.next()) {
                inst.add(new Instance(rs.getInt("id_i"),
                        new Book(rs.getInt("id_book"),rs.getString("name_b")),
                        rs.getInt("year_b"),
                        rs.getString("publish"),
                        rs.getInt("cost"),
                        rs.getInt("status"),
                        rs.getString("comments")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //if no instances
        if(inst.isEmpty()) return new ArrayList<>();;

        List<AuthBook> authbooks=new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a, id_b, name_b  FROM ((Book join AuBook on book_id=id_b) join Author on " +
                            "id_a=author_id)")) {
            while (rs.next()) {
                authbooks.add(new AuthBook
                        (new Author(rs.getInt("id_a"),
                                rs.getString("name_f"),
                                rs.getString("name_s"),
                                rs.getString("name_p"),
                                rs.getInt("year_a")),
                                new Book(rs.getInt("id_b"),
                                        rs.getString("name_b"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(authbooks.isEmpty()) return new ArrayList<>();

        Map<Instance, List<Author>> mapinst;
        List<Author> auth;
        for (Instance instance: inst) {
            auth = new ArrayList<>();
            mapinst = new HashMap<>();
            for (AuthBook au:authbooks) {
                if(instance.getBook().getId_b()==au.getBook().getId_b()) {
                    auth.add(au.getAuthor());
                }
            }
            mapinst.put(instance,auth);
            instances.add(mapinst);
        }
        return instances;
    }

    @Override
    public List<Map<Instance, List<Author>>> getInstanceByCondition(Author author, Book book) {
        String additionalForBook;
        String additionalForAuthors;
        additionalForBook=parseForBook(book);
        additionalForAuthors=parseForAuthors(author);
        List<Map<Instance, List<Author>>> instances = new ArrayList<>();

        List<Instance> inst = new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_i, id_book, year_b, publish, cost, status, comments, name_b FROM (Instance join Book on " +
                            "id_book=id_b) " + additionalForBook+" 1=1 and id_b in " +
                            "(select au.book_id from ((Author auth join AuBook au on auth.id_a=au.author_id) join Instance inst on " +
                            "inst.id_book=au.book_id )" +
                            additionalForAuthors + " 1=1)")) {
            while (rs.next()) {
                inst.add(new Instance(rs.getInt("id_i"),
                        new Book(rs.getInt("id_book"),rs.getString("name_b")),
                        rs.getInt("year_b"),
                        rs.getString("publish"),
                        rs.getInt("cost"),
                        rs.getInt("status"),
                        rs.getString("comments")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(inst);
        //if no instances
        if(inst.isEmpty()) return new ArrayList<>();;

        List<AuthBook> authbooks=new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a, id_b, name_b  FROM ((Book join AuBook on book_id=id_b) join Author on " +
                            "id_a=author_id) " + additionalForAuthors + " 1=1")) {
            while (rs.next()) {
                authbooks.add(new AuthBook
                        (new Author(rs.getInt("id_a"),
                                rs.getString("name_f"),
                                rs.getString("name_s"),
                                rs.getString("name_p"),
                                rs.getInt("year_a")),
                                new Book(rs.getInt("id_b"),
                                        rs.getString("name_b"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(authbooks.isEmpty()) return new ArrayList<>();

        Map<Instance, List<Author>> mapinst;
        List<Author> auth;
        for (Instance instance: inst) {
            auth = new ArrayList<>();
            mapinst = new HashMap<>();
            for (AuthBook au : authbooks) {
                if (instance.getBook().getId_b() == au.getBook().getId_b()) {
                    auth.add(au.getAuthor());
                }
            }
            mapinst.put(instance, auth);
            instances.add(mapinst);
        }
        return instances;
    }

    @Override
    public  List<Author> getListOfAuthors(Instance instance) {
        List<Author> authors = new ArrayList<>();
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement();
            final ResultSet rs = statement.executeQuery(
                    "SELECT id_a, name_f, name_s, name_p, year_a, id_b, name_b  FROM ((Book join AuBook on book_id=id_b) join Author on " +
                            "id_a=author_id) where id_b=" + instance.getBook().getId_b())) {
            while (rs.next()) {
                authors.add(new Author(rs.getInt("id_a"),
                                rs.getString("name_f"),
                                rs.getString("name_s"),
                                rs.getString("name_p"),
                                rs.getInt("year_a")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    private String parseForAuthors(Author author) {
        StringBuffer expression = new StringBuffer(" where ");
        String tmp;
        if(!(tmp = author.getName_f()).isEmpty()) expression.append(" name_f='" + tmp + "' and");
        if(!(tmp = author.getName_s()).isEmpty()) expression.append(" name_s='" + tmp + "' and");
        if(!(tmp = author.getName_p()).isEmpty()) expression.append(" name_p='" + tmp + "' and");
        return expression.toString();
    }

    private String parseForBook(Book book) {
        StringBuffer expression = new StringBuffer(" where ");
        String tmp;
        if(!(tmp = book.getName_b()).isEmpty()) expression.append(" name_b='" + tmp + "' and ");
        return expression.toString();
    }


    // give us author and book and instance info: publih, year_b
    //TODO:add list<Author>
    @Override
    public boolean insertInstance(Author author, Instance instance) {
        Connections.getFactory().getAuthorDao().insertAuthor(author);
        int id_a = Connections.getFactory().getAuthorDao().findAuthor(author);
        if (id_a == -1) return false;
        //insert book
        Connections.getFactory().getBookDao().insertBook(instance.getBook());
        int id_b = Connections.getFactory().getBookDao().findBook(instance.getBook());
        if (id_b == -1) return false;

        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            statement.executeUpdate(
                    "insert into AuBook (author_id, book_id) " +
                            "values (" + id_a + "," + id_b + ")" );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "insert into Instance (id_book, year_b, publish, cost, status, comments) " +
                            "values (" + id_b + ","+ instance.getYear_b() + ", '" +
                            instance.getPublish() +"' ," + instance.getCost() + ", " +
                            instance.getStatus() + ", '" + instance.getComments()+"')" ) >0 ? true : false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        return false;
    }

    @Override
    public boolean deleteInstanceById(int id) {
        try(final Connection connection = OracleDAOFactory.getConnection();
            final Statement statement = connection.createStatement()){

            return statement.executeUpdate(
                    "Delete from Instance where id_i = " + id ) > 0 ? true : false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
