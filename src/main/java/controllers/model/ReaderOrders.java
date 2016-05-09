package main.java.controllers.model;

/**
 * Created by igor on 22.04.16.
 */

/**
 * Class ReaderOrders
 * @author igor
 */
public class ReaderOrders {
    private Reader reader;
    private Book book;
    private String publish;

    public ReaderOrders(Reader reader, Book book, String publish) {
        this.reader = reader;
        this.book = book;
        this.publish = publish;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReaderOrders that = (ReaderOrders) o;

        if (reader != null ? !reader.equals(that.reader) : that.reader != null) return false;
        if (book != null ? !book.equals(that.book) : that.book != null) return false;
        return publish != null ? publish.equals(that.publish) : that.publish == null;

    }

    @Override
    public int hashCode() {
        int result = reader != null ? reader.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        result = 31 * result + (publish != null ? publish.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReaderOrders{" +
                "reader=" + reader +
                ", book=" + book +
                ", publish='" + publish + '\'' +
                '}';
    }
}
