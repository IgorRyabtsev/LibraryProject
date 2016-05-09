package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */

/**
 * Abstract Class OrdersConditon for finding Author by condition,
 * realization is {@link main.java.controllers.DAO.Oracle.condition.OracleOrdersCondition}
 * @author igor
 */

public abstract class OrdersCondition implements Condition {
    private String id_o;
    private String reader_id;
    private String instance_id;
    private String release_date;
    private String return_date;

    /**
     * Getting order id
     * @return  order id
     */
    public String getId_o() {
        return id_o;
    }

    /**
     * Setting order id
     * @param id_o
     */
    public void setId_o(String id_o) {
        this.id_o = id_o;
    }

    /**
     * Getting reader id
     * @return reader id
     */
    public String getReader_id() {
        return reader_id;
    }

    /**
     * Setting reader id
     * @param reader_id
     */
    public void setReader_id(String reader_id) {
        this.reader_id = reader_id;
    }

    /**
     * Getting Instance id
     * @return instance id
     */
    public String getInstance_id() {
        return instance_id;
    }

    /**
     * Setting instance id
     * @param instance_id
     */
    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    /**
     * Getting release date of book
     * @return release date
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     * Setting release date of book
     * @param release_date
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     * Getting return date of book
     * @return return date
     */
    public String getReturn_date() {
        return return_date;
    }

    /**
     * Setting return date of book
     * @param return_date
     */
    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }
}
