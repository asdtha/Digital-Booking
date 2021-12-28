package grupo4.backend.repository;

import grupo4.backend.model.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query(
            value = "SELECT P.*, C.name FROM products P inner join cities C on C.id = P.city_id WHERE C.name like %:city_name%", nativeQuery = true)
    List<Product> getProductsByCity(@Param("city_name") String city_name);

    @Query(
            value = "SELECT P.*, C.name FROM products P inner join categories C on C.id = P.category_id WHERE C.name like %:category_name%", nativeQuery = true)
    List<Product> getProductsByCategory(@Param("category_name") String category_name);

    @Query( //traer todos los productos que cumplan: cada una de mis reservas o terminan antes (o igual a) de CheckInDate o empiezan igual o despues de checkOutDate.
            //value = "SELECT P.* from products p left join bookings b on p.id = b.product_id where p.id = any( select b.product_id from bookings b where b.check_out_date <= :checkInDate or b.check_in_date >= :checkOutDate) or b.product_id is null", nativeQuery = true)
            value = "select P.* from products P " +
                    "where P.city_id = :cityId " +
                    "and P.id not in ( " +
                    "    select distinct B.product_id " +
                    "    from bookings B " +
                    "    where (B.check_out_date > :checkInDate and B.check_in_date < :checkOutDate) " +
                    ")" +
                    " group by P.id; ", nativeQuery = true)
    List<Product> getProductsByCityAndDates(@Param("cityId") Long cityId, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);


    @Query(
            value = "Select P.* From products P where p.city_id = :cityId", nativeQuery = true)
    List<Product> getProductsByCityId(@Param("cityId") Long cityId);

    @Query(
            value = "Select P.* From products P where p.category_id = :categoryId", nativeQuery = true)
    List<Product> getProductsByCategoryId(@Param("categoryId") Long categoryId);


    @Query(
            value = "select P.* from products P " +
                    "where (:cityId is null OR P.city_id = :cityId)" +
                    "and (:categoryId is null  or P.category_id = :categoryId)" +
                    "and ((:checkInDate is null and :checkOutDate is null) or P.id not in (" +
                    " select distinct B.product_id from bookings B where " +
                    " (B.check_out_date > :checkInDate and B.check_in_date < :checkOutDate) " +
                    "))", nativeQuery = true)
    List<Product> getProductsByFilter(@Param("categoryId") Long categoryId, @Param("cityId") Long cityId, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);

    @Query(
            value = "select P.* from products P " +
                    "where (:cityId is null OR P.city_id = :cityId)" +
                    "and (:categoryId is null  or P.category_id = :categoryId)" +
                    "and ((:checkInDate is null and :checkOutDate is null) or P.id not in (" +
                    " select distinct B.product_id from bookings B where " +
                    " (B.check_out_date > :checkInDate and B.check_in_date < :checkOutDate) " +
                    ")) limit :limitValue offset :offsetValue", nativeQuery = true)
    List<Product> getProductsByFilterWithOffset(@Param("categoryId") Long categoryId, @Param("cityId") Long cityId, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate, @Param("limitValue") Integer limitValue, @Param("offsetValue") Integer offsetValue);
}
