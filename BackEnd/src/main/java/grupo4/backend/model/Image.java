package grupo4.backend.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;

@Entity
@Table(name = "images")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Image.class)
@JsonIgnoreProperties(value = "product")
public class Image {

    //Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn( name = "product_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Product product;
    @Column
    private String title;
    @Column
    private String url;

    //Constructors
    public Image() {
    }

    //Getters and Setters
    public Long getId() {
       return id;
   }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    //To String
    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", product=" + product +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
