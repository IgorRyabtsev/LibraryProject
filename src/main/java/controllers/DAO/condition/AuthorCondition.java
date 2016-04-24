package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */
public abstract class AuthorCondition implements Condition {
    private String id_a;
    private String name_f;
    private String name_s;
    private String name_p;
    private String year_a;

    public String getId_a() {
        return id_a;
    }

    public void setId_a(String id_a) {
        this.id_a = id_a;
    }

    public String getName_f() {
        return name_f;
    }

    public void setName_f(String name_f) {
        this.name_f = name_f;
    }

    public String getName_s() {
        return name_s;
    }

    public void setName_s(String name_s) {
        this.name_s = name_s;
    }

    public String getName_p() {
        return name_p;
    }

    public void setName_p(String name_p) {
        this.name_p = name_p;
    }

    public String getYear_a() {
        return year_a;
    }

    public void setYear_a(String year_a) {
        this.year_a = year_a;
    }
}
