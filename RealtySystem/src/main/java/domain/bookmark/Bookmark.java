package domain.bookmark;

import domain.client.Client;
import domain.realty_obj.RealtyObject;

import javax.persistence.*;
import java.awt.print.Book;
import java.util.Date;

/***
 * Represents a bookmark
 */
@Entity
@Table(name = "bookmarks")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Integer id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "realty_object_id", nullable = true)
    private RealtyObject realtyObject;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /***
     * Constructor
     * @param name name
     * @param client client
     * @param realtyObject realty object
     */
    public Bookmark(String name, Client client, RealtyObject realtyObject) {
        if (name == null) {
            throw new IllegalArgumentException("Bookmark: name is null!");
        }
        if (client == null) {
            throw new IllegalArgumentException("Bookmark: client is null!");
        }
        if (realtyObject == null) {
            throw new IllegalArgumentException("Bookmark: realty object is null!");
        }
        this.name = name;
        this.client = client;
        this.realtyObject = realtyObject;
        creationDate = new Date();
    }

    public Bookmark(Bookmark b) {
        id = b.id;
        name = b.name;
        client = new Client(b.client);
        realtyObject = new RealtyObject(b.realtyObject);
        creationDate = b.creationDate;
    }

    public Bookmark() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealtyObject getRealtyObject() {
        return realtyObject;
    }

    public void setRealtyObject(RealtyObject realtyObject) {
        this.realtyObject = realtyObject;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Client getClient() {
        return client;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookmark bookmark = (Bookmark) o;

        if (id != null ? !id.equals(bookmark.id) : bookmark.id != null) return false;
        if (name != null ? !name.equals(bookmark.name) : bookmark.name != null) return false;
        if (client != null ? !client.equals(bookmark.client) : bookmark.client != null) return false;
        if (realtyObject != null ? !realtyObject.equals(bookmark.realtyObject) : bookmark.realtyObject != null)
            return false;
        return !(creationDate != null ? !creationDate.equals(bookmark.creationDate) : bookmark.creationDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (realtyObject != null ? realtyObject.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                ", name='" + name + '\'' +
                ", client=" + client +
                ", realtyObject=" + realtyObject +
                ", creationDate=" + creationDate +
                '}';
    }
}
