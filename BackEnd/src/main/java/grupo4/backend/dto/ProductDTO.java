package grupo4.backend.dto;
import grupo4.backend.model.*;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO implements Serializable {

    //Attributes
    private Long id;
    private String name;
    private String description;
    private Double longitude;
    private Double latitude;
    private String streetName;
    private String streetNumber;
    private String cancelationPolicy;
    private String healthAndSafetyPolicy;
    private String houseRulesPolicy;
    private City city;
    private Category category;
    private List<Image> images;
    private List <Characteristic> characteristics;
    private List<BookingDTO> bookingList;
    private PuntuationCounterDTO puntuationCounterDTO;
    private Integer earliestCheckInHour;
    private Integer latestCheckInHour;



    //Constructors
    public ProductDTO() {
    }

    public ProductDTO(Product product) {
        this.id= product.getId();
        this.name= product.getName();
        this.description= product.getDescription();
        this.latitude= product.getLatitude();
        this.longitude= product.getLongitude();
        this.streetName=product.getStreetName();
        this.streetNumber= product.getStreetNumber();
        this.cancelationPolicy = product.getCancelationPolicy();
        this.houseRulesPolicy = product.getHouseRulesPolicy();
        this.healthAndSafetyPolicy = product.getHealthAndSafetyPolicy();
        this.images=product.getImages();
        this.city= product.getCity();
        this.category= product.getCategory();
        this.characteristics= product.getCharacteristics();
        this.bookingList = convertBookingsToBookingsDTO(product.getBookingList());
        if(product.getPuntuationCounter()!= null) {
            this.puntuationCounterDTO = new PuntuationCounterDTO(product.getPuntuationCounter());
        }
        this.earliestCheckInHour = product.getEarliestCheckInHour();
        this.latestCheckInHour = product.getLatestCheckInHour();
    }


    public ProductDTO(Long id, String name, String description, Double latitude, Double longitude, String streetName, String streetNumber, List<Image> images, Category category, City city, List <Characteristic> characteristics, List<BookingDTO> bookingList) {
        this.id=id;
        this.name= name;
        this.description= description;
        this.latitude=latitude;
        this.longitude= longitude;
        this.streetName= streetName;
        this.streetNumber= streetNumber;
        this.images=images;
        this.category= category;
        this.city=city;
        this.characteristics=characteristics;
        this.bookingList = bookingList;
    }

    //Methods
    private List<BookingDTO> convertBookingsToBookingsDTO(List<Booking> bookingList) {
        if (bookingList == null) {
            return new ArrayList<>();
        } else {
            List<BookingDTO> calendar = new ArrayList<BookingDTO>();
            LocalDate today = LocalDate.now();
            for (Booking booking : bookingList
            ) {
                boolean reservationAlreadyEnded = today.isBefore(booking.getCheckOutDate());
                if (reservationAlreadyEnded) {
                    BookingDTO interval = new BookingDTO(booking);
                    calendar.add(interval);
                }
            }
            return calendar;
        }
    };

    //Getters and Setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Double getLongitude() {return longitude;}
    public void setLongitude(Double longitude) {this.longitude = longitude;}
    public Double getLatitude() {return latitude;}
    public void setLatitude(Double latitude) {this.latitude = latitude;}
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
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }
    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }
    public Category getCategory() {return category;}
    public void setCategory(Category category) {this.category = category;}
    public List<BookingDTO> getBookingList(){return bookingList;}
    public void setBookingList(List<Booking> bookingList){this.bookingList = convertBookingsToBookingsDTO(bookingList);}
    public PuntuationCounterDTO getPuntuationCounterDTO() {
        return puntuationCounterDTO;
    }
    public void setPuntuationCounterDTO(PuntuationCounterDTO puntuationCounterDTO) {this.puntuationCounterDTO = puntuationCounterDTO;}
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
}
