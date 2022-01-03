package grupo4.backend.service;
import grupo4.backend.dto.CategoryDTO;
import grupo4.backend.dto.CityDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Category;
import java.util.List;

public interface CategoryService {

    CategoryDTO searchCategoryById(Long id) throws BadRequestException;
    Category saveCategory(CategoryDTO product) throws BadRequestException;
    CategoryDTO updateCategory(CategoryDTO product) throws BadRequestException;
    boolean deleteCategoryById(Long id) throws BadRequestException;
    List<CategoryDTO> searchAllCategories();
    CategoryDTO searchCategoryByName (String categoryName) throws BadRequestException;

}



