package main.java.controllers.model;

import java.sql.Date;

/**
 * Created by igor on 22.04.16.
 */

/**
 * Class Orders
 * @author igor
 */

public class Orders {
    private final int id_o;
    private Reader reader;
    private Instance instance;
    // book's release date
    private Date release_date;
    // book's return date
    private Date return_date;

    public Orders() {
        id_o=-1;
    }

    public Orders(int id_o, Reader reader, Instance instance, Date release_date, Date return_date) {
        this.id_o = id_o;
        this.reader = reader;
        this.instance = instance;
        this.release_date = release_date;
        this.return_date = return_date;
    }

    public int getId_o() {
        return id_o;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (id_o != orders.id_o) return false;
        if (reader != null ? !reader.equals(orders.reader) : orders.reader != null) return false;
        if (instance != null ? !instance.equals(orders.instance) : orders.instance != null) return false;
        if (release_date != null ? !release_date.equals(orders.release_date) : orders.release_date != null)
            return false;
        return return_date != null ? return_date.equals(orders.return_date) : orders.return_date == null;

    }

    @Override
    public int hashCode() {
        int result = id_o;
        result = 31 * result + (reader != null ? reader.hashCode() : 0);
        result = 31 * result + (instance != null ? instance.hashCode() : 0);
        result = 31 * result + (release_date != null ? release_date.hashCode() : 0);
        result = 31 * result + (return_date != null ? return_date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id_o=" + id_o +
                ", reader=" + reader +
                ", instance=" + instance +
                ", release_date=" + release_date +
                ", return_date=" + return_date +
                '}';
    }
}
