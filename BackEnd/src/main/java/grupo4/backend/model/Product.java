package grupo4.backend.model;
import com.fasterxml.jackson.annotation.*;
import grupo4.backend.model.punctuation.PunctuationCounter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table (name = "products")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Product.class)

public class Product {

    //Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String description;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private String streetName;
    @Column
    private String streetNumber;
    @Column(length = 1000)
    private String cancelationPolicy;
    @Column(length = 1000)
    private String healthAndSafetyPolicy;
    @Column(length = 1000)
    private String houseRulesPolicy;
    @Column
    private String name;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images;
    @ManyToOne
    @JoinColumn( name = "city_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private City city;
    @ManyToOne
    @JoinColumn( name = "category_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;
    @ManyToMany(cascade={CascadeType.MERGE})
    @JoinTable(name="Products_Characteristics", joinColumns=@JoinColumn(name="products_id"),
            inverseJoinColumns=@JoinColumn(name="characteristics_id"))
    private List <Characteristic> characteristics;
    @OneToOne(mappedBy= "product", cascade = CascadeType.ALL)
    private PunctuationCounter punctuationCounter;
    @OneToMany(mappedBy= "product", cascade = CascadeType.ALL)
    private List<Booking> bookingList;
    private Integer earliestCheckInHour;
    private Integer latestCheckInHour;



    //Constructors
    public Product(){
    }

    public void createAndSetNewPuntuationCounter(){
        PunctuationCounter justCreated = new PunctuationCounter();
        justCreated.setProduct(this);
        this.setPuntuationCounter(justCreated);
    }

    //Methods
    public void addBooking(Booking b){bookingList.add(b);}

    //Getters and Setters
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getLongitude() {return longitude;}
    public void setLongitude(Double longitude) { this.longitude = longitude;}
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public String getStreetNumber() {
        return streetNumber;
    }
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
    public String getCancelationPolicy() {
        return cancelationPolicy;
    }
    public void setCancelationPolicy(String cancelationPolicy) {
        this.cancelationPolicy = cancelationPolicy;
    }
    public String getHealthAndSafetyPolicy() {
        return healthAndSafetyPolicy;
    }
    public void setHealthAndSafetyPolicy(String healthAndSafetyPolicy) {this.healthAndSafetyPolicy = healthAndSafetyPolicy;}
    public String getHouseRulesPolicy() {
        return houseRulesPolicy;
    }
    public void setHouseRulesPolicy(String houseRulesPolicy) {
        this.houseRulesPolicy = houseRulesPolicy;
    }
    public List<Image> getImages() {return images;}
    public void setImages(List<Image> images) {this.images = images;}
    public City getCity() {return city;}
    public void setCity(City city) {this.city = city;}
    public Double getLatitude() {return latitude;}
    public void setLatitude(Double latitude) {this.latitude = latitude;}
    public Category getCategory() { return category;}
    public void setCategory(Category category) {this.category = category;}
    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }
    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
    public PunctuationCounter getPuntuationCounter() {
        return this.punctuationCounter;
    }
    public void setPuntuationCounter(PunctuationCounter punctuationCounter) {this.punctuationCounter = punctuationCounter;}
    public List<Booking> getBookingList() {
        return bookingList;
    }
    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }
    public Integer getEarliestCheckInHour() {
        return earliestCheckInHour;
    }
    public void setEarliestCheckInHour(Integer earliestCheckInHour) {
        this.earliestCheckInHour = earliestCheckInHour;
    }
    public Integer getLatestCheckInHour() {
        return latestCheckInHour;
    }
    public void setLatestCheckInHour(Integer latestCheckInHour) {
        this.latestCheckInHour = latestCheckInHour;
    }

    //To String
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", images=" + images +
                ", city=" + city +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
