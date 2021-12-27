package grupo4.backend.model;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table (name = "favorites")
public class Favorite {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private UserDB user;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    //Constructors

    public Favorite() {
    }

    //Getters and setters

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public UserDB getUserDb() {return user;}
    public UserDB getUser() {
        return user;
    }
    public void setUserDb(UserDB user) {this.user = user;}
    public void setUser(UserDB user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    //To string
    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }


}
