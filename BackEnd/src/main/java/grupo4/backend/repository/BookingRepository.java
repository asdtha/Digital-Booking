package grupo4.backend.repository;

import grupo4.backend.model.Booking;
import grupo4.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(
            value = "SELECT B.* FROM bookings B WHERE B.product_id = :productId", nativeQuery = true)
    List<Booking> getBookingByProductId(@Param("productId") Long productId);

    @Query(
            value = "SELECT B.* FROM bookings B WHERE B.user_id = :userId", nativeQuery = true)
    List<Booking> getBookingByUserId(@Param("userId") Long userId);

}