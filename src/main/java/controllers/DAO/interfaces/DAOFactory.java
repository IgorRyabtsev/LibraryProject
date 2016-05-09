package main.java.controllers.DAO.interfaces;


import main.java.controllers.DAO.condition.AuthorCondition;
import main.java.controllers.DAO.condition.BookCondition;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Interface DaoFactory for realization {@link main.java.controllers.DAO.Oracle.OracleDAOFactory}
 * @author igor
 */

public interface DAOFactory {

    /**
     * Return reader dao
     * @return ReaderDao
     */
    ReaderDao getReaderDao();

    /**
     * Return author dao
     * @return AuthorDao
     */
    AuthorDao getAuthorDao();

    /**
     * Return author condition
     * @return AuthorCondition
     */
    AuthorCondition getAuthorConditionDao();

    /**
     * Return book dao
     * @return BookDao
     */
    BookDao getBookDao();

    /**
     * Return instance dao
     * @return InstanceDao
     */
    InstanceDao getInstanceDao();

    /**
     * Return order dao
     * @return OrdersDao
     */
    OrdersDao getOrdersDao();

    /**
     * Return Reader-Orders Dao
     * @return ReaderOrdersDao
     */
    ReaderOrdersDao getReaderOrdersDao();
}
