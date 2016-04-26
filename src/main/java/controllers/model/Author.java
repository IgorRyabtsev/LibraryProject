package main.java.controllers.model;

/**
 * Created by igor on 21.04.16.
 */
public class Author {
    private final int id_a;
    private final String name_f;
    private final String name_s;
    private final String name_p;
    private int year_a;

    public Author() {
        this.id_a = -1;
        this.name_f = null;
        this.name_s = null;
        this.name_p = null;
    }

    public Author(int id_a, String name_f, String name_s, String name_p, int year_a) {
        this.id_a = id_a;
        this.name_f = name_f;
        this.name_s = name_s;
        this.name_p = name_p;
        this.year_a = year_a;
    }

    public int getId_a() {
        return id_a;
    }

    public String getName_f() {
        return name_f;
    }

    public String getName_s() {
        return name_s;
    }

    public String getName_p() {
        return name_p;
    }

    public int getYear_a() {
        return year_a;
    }

    public void setYear_a(int year_a) {
        this.year_a = year_a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id_a != author.id_a) return false;
        if (year_a != author.year_a) return false;
        if (name_f != null ? !name_f.equals(author.name_f) : author.name_f != null) return false;
        if (name_s != null ? !name_s.equals(author.name_s) : author.name_s != null) return false;
        return name_p != null ? name_p.equals(author.name_p) : author.name_p == null;

    }

    @Override
    public int hashCode() {
        int result = id_a;
        result = 31 * result + (name_f != null ? name_f.hashCode() : 0);
        result = 31 * result + (name_s != null ? name_s.hashCode() : 0);
        result = 31 * result + (name_p != null ? name_p.hashCode() : 0);
        result = 31 * result + year_a;
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id_a=" + id_a +
                ", name_f='" + name_f + '\'' +
                ", name_s='" + name_s + '\'' +
                ", name_p='" + name_p + '\'' +
                ", year_a=" + year_a +
                '}';
    }
}
