package grupo4.backend.dto;
import grupo4.backend.model.City;
import grupo4.backend.model.Favorite;
import grupo4.backend.model.Product;
import grupo4.backend.model.UserDB;

public class FavoriteDTO {

    //Attributes
    private Long id;
    private Long userId;
    private Long productId;

    //Constructors

    public FavoriteDTO() {
    }

    public FavoriteDTO(Favorite favorite){
        this.id= favorite.getId();
        this.userId= favorite.getUserDb().getId();
        this.productId = favorite.getProduct().getId();
    }

    //Getters and Setters


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
