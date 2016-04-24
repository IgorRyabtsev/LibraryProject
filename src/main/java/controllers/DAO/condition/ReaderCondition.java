package main.java.controllers.DAO.condition;

/**
 * Created by igor on 14.04.16.
 */
public abstract class ReaderCondition implements Condition {
    private String id_r;
    private String namer_f;
    private String namer_s;
    private String namer_p;
    private String email;

    public String getId_r() {
        return id_r;
    }

    public void setId_r(String id_r) {
        this.id_r = id_r;
    }

    public String getNamer_f() {
        return namer_f;
    }

    public void setNamer_f(String namer_f) {
        this.namer_f = namer_f;
    }

    public String getNamer_s() {
        return namer_s;
    }

    public void setNamer_s(String namer_s) {
        this.namer_s = namer_s;
    }

    public String getNamer_p() {
        return namer_p;
    }

    public void setNamer_p(String namer_p) {
        this.namer_p = namer_p;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
