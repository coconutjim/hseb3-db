package domain.realty_obj;

import domain.realtor.Realtor;

import javax.persistence.*;
import java.util.Date;

/***
 * Represents a realty object
 */
@Entity
@Table(name = "realty_objects")
public class RealtyObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "realty_object_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Float square;

    @Column(nullable = false)
    private Float price;

    @Column
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "realtor_id", nullable = false)
    private Realtor realtor;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /***
     * Constructor
     * @param name name
     * @param city city
     * @param address address
     * @param square square
     * @param price price
     * @param description description
     * @param imageUrl link tp image
     * @param realtor realtor
     */
    public RealtyObject(String name, String city, String address, Float square,
                        Float price, String description, String imageUrl, Realtor realtor) {
        if (name == null) {
            throw new IllegalArgumentException("RealtyObject: name is null!");
        }
        if (city == null) {
            throw new IllegalArgumentException("RealtyObject: cityId is null!");
        }
        if (address == null) {
            throw new IllegalArgumentException("RealtyObject: address is null!");
        }
        if (square == null) {
            throw new IllegalArgumentException("RealtyObject: square is null!");
        }
        if (price == null) {
            throw new IllegalArgumentException("RealtyObject: price is null!");
        }
        if (realtor == null) {
            throw new IllegalArgumentException("RealtyObject: realtor is null!");
        }
        this.name = name;
        this.city = city;
        this.address = address;
        this.square = square;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.realtor = realtor;
        creationDate = new Date();
    }

    public RealtyObject(RealtyObject ro) {
        id = ro.id;
        name = ro.name;
        city = ro.city;
        address = ro.address;
        square = ro.square;
        price = ro.price;
        description = ro.description;
        imageUrl = ro.imageUrl;
        realtor = new Realtor(ro.realtor);
        creationDate = ro.creationDate;
    }

    /***
     * For Hibernate
     */
    public RealtyObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getSquare() {
        return square;
    }

    public void setSquare(Float square) {
        this.square = square;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getId() {
        return id;
    }

    public Realtor getRealtor() {
        return realtor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RealtyObject that = (RealtyObject) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (square != null ? !square.equals(that.square) : that.square != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        if (realtor != null ? !realtor.equals(that.realtor) : that.realtor != null) return false;
        return !(creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (square != null ? square.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (realtor != null ? realtor.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RealtyObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", square=" + square +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", realtor=" + realtor +
                ", creationDate=" + creationDate +
                '}';
    }
}
