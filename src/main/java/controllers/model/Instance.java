package main.java.controllers.model;

/**
 * Created by igor on 21.04.16.
 */
public class Instance {
    private final int id_i;
    private final Book book;
    private final int year_b;
    private final String publish;
    private final int cost;
    private int status;
    private String comments;

    public Instance(int id_i, Book book, int year_b, String publish, int cost, int status, String comments) {
        this.id_i = id_i;
        this.book = book;
        this.year_b = year_b;
        this.publish = publish;
        this.cost = cost;
        this.status = status;
        this.comments = comments;
    }

    public int getId_i() {
        return id_i;
    }

    public Book getBook() {
        return book;
    }

    public int getYear_b() {
        return year_b;
    }

    public String getPublish() {
        return publish;
    }

    public int getCost() {
        return cost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instance instance = (Instance) o;

        if (id_i != instance.id_i) return false;
        if (year_b != instance.year_b) return false;
        if (cost != instance.cost) return false;
        if (status != instance.status) return false;
        if (book != null ? !book.equals(instance.book) : instance.book != null) return false;
        if (publish != null ? !publish.equals(instance.publish) : instance.publish != null) return false;
        return comments != null ? comments.equals(instance.comments) : instance.comments == null;

    }

    @Override
    public int hashCode() {
        int result = id_i;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + year_b;
        result = 31 * result + (publish != null ? publish.hashCode() : 0);
        result = 31 * result + cost;
        result = 31 * result + status;
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Instance{" +
                "id_i=" + id_i +
                ", book=" + book +
                ", year_b=" + year_b +
                ", publish='" + publish + '\'' +
                ", cost=" + cost +
                ", status=" + status +
                ", comments='" + comments + '\'' + "\n"+
                '}';
    }
}
