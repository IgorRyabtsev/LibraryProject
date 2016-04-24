package main.java.controllers.model;

/**
 * Created by igor on 23.04.16.
 */
public class AuthBook {
    private final Author author;
    private final Book book;

    public AuthBook(Author author, Book book) {
        this.author = author;
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthBook authBook = (AuthBook) o;

        if (author != null ? !author.equals(authBook.author) : authBook.author != null) return false;
        return book != null ? book.equals(authBook.book) : authBook.book == null;

    }

    @Override
    public int hashCode() {
        int result = author != null ? author.hashCode() : 0;
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthBook{" +
                "author=" + author +
                ", book=" + book +
                '}';
    }
}
