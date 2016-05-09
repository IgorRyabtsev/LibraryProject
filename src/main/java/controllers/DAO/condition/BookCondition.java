package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */

/**
 * Abstract Class BookConditon for finding Book by condition,
 * realization is {@link main.java.controllers.DAO.Oracle.condition.OracleBookCondition}
 * @author igor
 */

public abstract class BookCondition implements Condition {
    private String id_b;
    private String name_b;

    /**
     * Get book id
     * @return book id
     */
    public String getId_b() {
        return id_b;
    }

    /**
     * Set book id
     * @param id_b
     */
    public void setId_b(String id_b) {
        this.id_b = id_b;
    }

    /**
     * Get name of book
     * @return book name
     */
    public String getName_b() {
        return name_b;
    }

    /**
     * Set book name
     * @param name_b
     */
    public void setName_b(String name_b) {
        this.name_b = name_b;
    }
}
