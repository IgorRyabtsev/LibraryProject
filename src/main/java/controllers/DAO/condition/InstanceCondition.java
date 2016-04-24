package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */
public abstract class InstanceCondition implements Condition {
    private String id_i;
    private String id_book;
    private String year_b;
    private String publish;
    private String cost;

    public String getId_i() {
        return id_i;
    }

    public void setId_i(String id_i) {
        this.id_i = id_i;
    }

    public String getId_book() {
        return id_book;
    }

    public void setId_book(String id_book) {
        this.id_book = id_book;
    }

    public String getYear_b() {
        return year_b;
    }

    public void setYear_b(String year_b) {
        this.year_b = year_b;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
