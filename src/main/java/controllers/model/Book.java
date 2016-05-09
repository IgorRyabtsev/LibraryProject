package main.java.controllers.model;

/**
 * Created by igor on 21.04.16.
 */

/**
 * Class Book
 * @author igor
 */

public class Book {
    private final int id_b;
    //book name
    private String name_b;

    public Book(int id_b, String name_b) {
        this.id_b = id_b;
        this.name_b = name_b;
    }

    public int getId_b() {
        return id_b;
    }

    public String getName_b() {
        return name_b;
    }

    public void setName_b(String name_b) {
        this.name_b = name_b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id_b != book.id_b) return false;
        return name_b != null ? name_b.equals(book.name_b) : book.name_b == null;

    }

    @Override
    public int hashCode() {
        int result = id_b;
        result = 31 * result + (name_b != null ? name_b.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id_b=" + id_b +
                ", name_b='" + name_b + '\'' +
                '}';
    }
}
