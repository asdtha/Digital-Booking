package grupo4.backend.service;
import grupo4.backend.dto.ProductDTO;
import grupo4.backend.util.ProductFilter;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Product;

import java.util.List;

public interface ProductService {

    ProductDTO searchProductById(Long id) throws BadRequestException;
    Product searchProductByIdAsClass(Long id) throws BadRequestException;
    Product saveProduct(ProductDTO product) throws BadRequestException;
    ProductDTO updateProduct(ProductDTO product) throws BadRequestException;
    boolean deleteProductById(Long id) throws BadRequestException;
    List<ProductDTO> searchAllProducts();
    List<ProductDTO> getProductsByCity(String cityName) throws BadRequestException;
    List<ProductDTO> getProductsByCategory(String categoryName) throws BadRequestException;
    List<ProductDTO> getProductsByCityAndDates(ProductFilter filter) throws BadRequestException;
    List<ProductDTO> getProductsByFilter(ProductFilter filter) throws BadRequestException;
}
