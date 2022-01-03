package grupo4.backend.controller;
import grupo4.backend.dto.ProductDTO;
import grupo4.backend.util.ProductFilter;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Product;
import grupo4.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/API/products")
//@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> searchProductById(@PathVariable Long id) throws BadRequestException{
        //ProductDTO foundProduct = productService.searchProductByIdWithCustomQueryAsDTO(id);
        ProductDTO foundProduct = productService.searchProductById(id);
        return ResponseEntity.ok(foundProduct);
    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductDTO>> searchAllProducts(){
        List<ProductDTO> productsList = productService.searchAllProducts();
        return ResponseEntity.ok(productsList);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Product> saveProduct(@RequestBody ProductDTO productDTO) throws BadRequestException {
        Product savedProduct = productService.saveProduct(productDTO);
        return ResponseEntity.ok(savedProduct);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws BadRequestException{
        ProductDTO updatedProduct = productService.updateProduct(productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws BadRequestException {
        if(id != null) {
            if(productService.deleteProductById(id)) {
                return ResponseEntity.ok("Se eliminó el producto con id: " + id);
            } else {
                return new ResponseEntity<>("No existe un producto con este id.",HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("No se encuentran datos del id proporcionado", HttpStatus.NO_CONTENT);
        }
    }

   @GetMapping("/getProductsByCity/{cityName}")
    public ResponseEntity <List<ProductDTO>> getProductsByCity (@PathVariable String cityName) throws BadRequestException{
        List<ProductDTO> productsByCity = productService.getProductsByCity(cityName);
        return ResponseEntity.ok(productsByCity);
    }


    @GetMapping("/getProductsByCategory/{categoryName}")
    public ResponseEntity <List<ProductDTO>> getProductsByCategory (@PathVariable String categoryName) throws BadRequestException{
        List<ProductDTO> productsByCategory = productService.getProductsByCategory(categoryName);
        return ResponseEntity.ok(productsByCategory);
    }


    @GetMapping("/filterByCityAndDates/{cityId}/{checkInDate}/{checkOutDate}")
    public ResponseEntity<List<ProductDTO>> filterByCityAndDates(@PathVariable Long cityId,@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) throws BadRequestException{
        ProductFilter filter = new ProductFilter();
        filter.setCheckInDate(checkInDate);
        filter.setCheckOutDate(checkOutDate);
        filter.setCityId(cityId);
        List<ProductDTO> filteredProducts = productService.getProductsByCityAndDates(filter);
        return ResponseEntity.ok(filteredProducts);
    }



    @GetMapping("/getProductsFiltered")
    public ResponseEntity <List<ProductDTO>> getProductsFiltered(HttpServletRequest request) throws BadRequestException{
        ProductFilter filter = new ProductFilter();
        String categoryId = request.getParameter("categoryId");
        String cityId = request.getParameter("cityId");
        String checkInDate = request.getParameter("checkInDate");
        String checkOutDate = request.getParameter("checkOutDate");
        String limit = request.getParameter("limit");
        String offset = request.getParameter("offset");
        try {
            if (categoryId != null) {
                filter.setCategoryId(Long.valueOf(categoryId));
            }
            if (cityId != null) {
                filter.setCityId(Long.valueOf(cityId));
            }
            if (checkInDate != null) {
                filter.setCheckInDate(LocalDate.parse(checkInDate));
            }
            if (checkOutDate != null) {
                filter.setCheckOutDate(LocalDate.parse(checkOutDate));
            }
            if (offset != null) {
                filter.setOffset(Integer.valueOf(offset));
            }
            if (limit != null) {
                filter.setLimit(Integer.valueOf(limit));
            }
        }catch(Exception e){
            throw new BadRequestException("Uno o más de los valores de la query no fue puesto en el formato correcto");
        }
        List<ProductDTO> filteredProducts = productService.getProductsByFilter(filter);
        return ResponseEntity.ok(filteredProducts);
    }
}
