package main.java.controllers.DAO.interfaces;

import main.java.controllers.model.Reader;

import java.util.List;

/**
 * Created by igor on 21.04.16.
 */
public interface ReaderDao {
    List<Reader> getAll();
    Reader getReaderById(int id);
    Reader getReaderByEmail(String email);
    boolean insertReader(Reader r);
    boolean deleteReaderById(int id);
    boolean findByEmail(String email);

}
