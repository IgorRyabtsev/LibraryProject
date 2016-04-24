package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */
public abstract class OrdersCondition implements Condition {
    private String id_o;
    private String reader_id;
    private String instance_id;
    private String release_date;
    private String return_date;

    public String getId_o() {
        return id_o;
    }

    public void setId_o(String id_o) {
        this.id_o = id_o;
    }

    public String getReader_id() {
        return reader_id;
    }

    public void setReader_id(String reader_id) {
        this.reader_id = reader_id;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }
}
