package grupo4.backend.repository;
import grupo4.backend.model.Category;
import grupo4.backend.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @Query(
            value = "SELECT C.* FROM categories C WHERE C.name like :category_name", nativeQuery = true)
    Category searchCategoryByName(@Param("category_name") String category_name);

}
