package main.java.controllers.DAO;


import main.java.controllers.DAO.Oracle.OracleDAOFactory;
import main.java.controllers.DAO.interfaces.DAOFactory;

/**
 * Created by igor on 15.04.16.
 */

/**
 * Class to get DAOFactory
 */
public class Connections {

    private static DAOFactory factory = null;

    /**
     * Returns DAOFactory
     * @return {@link OracleDAOFactory}
     */
    public static DAOFactory getFactory() {
        if (factory == null) {
            factory = new OracleDAOFactory();
        }
        return factory;
    }
}
