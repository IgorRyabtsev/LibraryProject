package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */
public abstract class BookCondition implements Condition {
    private String id_b;
    private String name_b;

    public String getId_b() {
        return id_b;
    }

    public void setId_b(String id_b) {
        this.id_b = id_b;
    }

    public String getName_b() {
        return name_b;
    }

    public void setName_b(String name_b) {
        this.name_b = name_b;
    }
}
