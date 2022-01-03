package grupo4.backend.controller;
import grupo4.backend.dto.CategoryDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Category;
import grupo4.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/API/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> searchCategoryById(@PathVariable Long id) throws BadRequestException{
        CategoryDTO dtoEncontrado = categoryService.searchCategoryById(id);
        return ResponseEntity.ok(dtoEncontrado);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> searchAllCategories(){
        List<CategoryDTO> todos = categoryService.searchAllCategories();
        return ResponseEntity.ok(todos);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @PostMapping
    public ResponseEntity saveCategory(@RequestBody CategoryDTO categoryDTO) throws BadRequestException {
        Category savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @PutMapping("/update")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) throws BadRequestException{
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) throws BadRequestException {
        if(id != null) {
            if(categoryService.deleteCategoryById(id)) {
                return ResponseEntity.ok("Fue eliminada la categoria con id: " + id);
            } else {
                return new ResponseEntity<>("No existe categoria con id "+ id,HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("No hay data en el id", HttpStatus.NO_CONTENT);
        }
    }



}
