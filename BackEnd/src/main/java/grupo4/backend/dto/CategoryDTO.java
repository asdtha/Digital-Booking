package grupo4.backend.dto;
import  com.fasterxml.jackson.annotation.JsonProperty;
import grupo4.backend.model.Category;
import java.io.Serializable;

public class CategoryDTO implements Serializable {

    //Attributes
    private Long id;
    private String name;
    private String description;
    private String imageURL;
    private Integer productCount;


    //Constructors
    public CategoryDTO() {
    }

    public CategoryDTO(String name, String description, String imageURL, Integer productCount) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.productCount = productCount;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription();
        this.imageURL = category.getImageURL();
        this.productCount = category.getProductsCount();
    }

    //Getters and Setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getImageURL() {return imageURL;}
    public void setImageURL(String imageURL) {this.imageURL = imageURL;}
    public Integer getProductCount() {
        return productCount;
    }
    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    //Equals
    @Override
    public boolean equals(Object o){
        CategoryDTO other = (CategoryDTO) o;
        boolean result = this.getId().equals(other.getId());
        result &= this.getName().equals(other.getName());
        result &= this.getDescription().equals(other.getDescription());
        result &= this.getImageURL().equals(other.getImageURL());
        return result;
    }
}
