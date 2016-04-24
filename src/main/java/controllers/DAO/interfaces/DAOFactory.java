package main.java.controllers.DAO.interfaces;


import main.java.controllers.DAO.condition.AuthorCondition;
import main.java.controllers.DAO.condition.BookCondition;

/**
 * Created by igor on 21.04.16.
 */
public interface DAOFactory {
    ReaderDao getReaderDao();
    AuthorDao getAuthorDao();
    AuthorCondition getAuthorConditionDao();
    BookDao getBookDao();
    BookCondition getBookConditionDao();
    InstanceDao getInstanceDao();
    OrdersDao getOrdersDao();
    ReaderOrdersDao getReaderOrdersDao();
}
