package grupo4.backend.model;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "categories")

public class Category{

    // Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String imageURL;
    @OneToMany(mappedBy= "category", cascade = CascadeType.ALL)
    private List<Product> products;


    // Constructors

    public Category(String name, String description, String imageURL) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
    }
    public Category() {
    }

    // GETTERS Y SETTERS
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName(){return name;}
    public void setName(String name) {this.name = name;}
    public String getDescription(){return description;}
    public void setDescription(String description) {this.description = description;}
    public String getImageURL() {return imageURL;}
    public void setImageURL(String imageURL) {this.imageURL = imageURL;}
    public Integer getProductsCount(){
        if(this.products == null){
            return 0;
        }else{
            return this.products.size();
        }
    }

    //To String
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }

    //Equals
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Category)){return false; }
        Category other = (Category) o;
        return this.id.equals(other.id) &&
                this.name.equals( other.name) &&
                this.description.equals( other.description)  &&
                this.imageURL.equals(other.imageURL);
    }
}
