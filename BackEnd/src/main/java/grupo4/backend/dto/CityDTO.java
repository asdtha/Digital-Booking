package grupo4.backend.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import grupo4.backend.model.City;
import grupo4.backend.model.Product;
import java.io.Serializable;
import java.util.List;


public class CityDTO implements Serializable {

    //Attributes
    private Long id;
    private String name;
    private String country_name;
    private List <Product> products;


    //Constructors
    public CityDTO(String name, String country_name) {
        this.name= name;
        this.country_name= country_name;

    }

    public CityDTO() {
    }

    public CityDTO(City city){
        this.id= city.getId();
        this.name= city.getName();
        this.country_name= city.getCountry_name();
        this.products=city.getProducts();

    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCountry_name() {
        return country_name;
    }
    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
    public Long getId() {
        return id;
    }
    public void setProducts(List<Product> products) {
      this.products = products;}


    //To String
    @Override
    public String toString() {
        return "CityDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country_name='" + country_name + '\'' +
                ", products=" + products +
                '}';
    }
}
