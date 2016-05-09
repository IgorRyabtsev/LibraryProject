package main.java.controllers.model;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Class Reader
 * @author igor
 */

public class Reader {
    private final int id_r;
    //first name
    private String namer_f;
    //second name
    private String namer_s;
    //patronymic name
    private String namer_p;
    //year of birth
    private final int year;
    private String email;
    private String password;
    private String role;

    public Reader() {
        id_r=-1;
        year=-1;
    }

    public Reader(int id_r, String namer_f, String namer_s, String namer_p, int year, String email, String password, String role) {
        this.id_r = id_r;
        this.namer_f = namer_f;
        this.namer_s = namer_s;
        this.namer_p = namer_p;
        this.year = year;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId_r() {
        return id_r;
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

    public int getYear() {
        return year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (id_r != reader.id_r) return false;
        if (year != reader.year) return false;
        if (namer_f != null ? !namer_f.equals(reader.namer_f) : reader.namer_f != null) return false;
        if (namer_s != null ? !namer_s.equals(reader.namer_s) : reader.namer_s != null) return false;
        if (namer_p != null ? !namer_p.equals(reader.namer_p) : reader.namer_p != null) return false;
        if (!email.equals(reader.email)) return false;
        if (!password.equals(reader.password)) return false;
        return role.equals(reader.role);

    }

    @Override
    public int hashCode() {
        int result = id_r;
        result = 31 * result + (namer_f != null ? namer_f.hashCode() : 0);
        result = 31 * result + (namer_s != null ? namer_s.hashCode() : 0);
        result = 31 * result + (namer_p != null ? namer_p.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id_r=" + id_r +
                ", namer_f='" + namer_f + '\'' +
                ", namer_s='" + namer_s + '\'' +
                ", namer_p='" + namer_p + '\'' +
                ", year=" + year +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
