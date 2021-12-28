package grupo4.backend.repository;
import grupo4.backend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends JpaRepository<City, Long>{
    @Query(
            value = "SELECT C.* FROM cities C WHERE C.name like :city_name", nativeQuery = true)
    City searchCityByName(@Param("city_name") String city_name);

}

