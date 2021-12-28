package grupo4.backend.repository;
import grupo4.backend.model.Booking;
import grupo4.backend.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {



    @Query(
            value = "SELECT F.id FROM favorites F WHERE F.product_id = :productId", nativeQuery = true)
    List<Long> findAllByIdWithProductId(@Param("productId") Long productId);

    @Query(
            value = "select 0 < (select count(*) from favorites where product_id = :productId  and user_id = :userId )", nativeQuery = true)
    Long existByUserAndProductId(Long productId, Long userId);

}

