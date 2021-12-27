package grupo4.backend.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.List;


@Entity
@Table (name = "cities")
@JsonIgnoreProperties(value = "products")
public class City {

    //Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String country_name;
    @OneToMany(mappedBy= "city", cascade = CascadeType.ALL)
    @JsonProperty("products")
    private List<Product> products;

    //Constructors
    public City(){
    };


    //Getters and Setters
    public String getCountry_name() {
        return country_name;
    }
    public void setCountry_name (String country_name) {
        this.country_name = country_name;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }


    //To String
    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country_name='" + country_name + '\'' +
                ", products=" + products +
                '}';
    }
}
