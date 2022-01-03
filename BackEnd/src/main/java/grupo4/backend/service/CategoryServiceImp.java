package grupo4.backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.dto.CategoryDTO;
import grupo4.backend.dto.CityDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Category;
import grupo4.backend.model.City;
import grupo4.backend.repository.ICategoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImp implements CategoryService{

    private final ICategoryRepository categoryRepository;
    private final ObjectMapper mapper;
    final static Logger logger = Logger.getLogger(CategoryServiceImp.class);

    @Autowired
    public CategoryServiceImp(ICategoryRepository categoryRepository, ObjectMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDTO searchCategoryById(Long id) throws BadRequestException {
        Optional<Category> wantedCategory = categoryRepository.findById(id);
        if(wantedCategory.isPresent()){
            return convertClassToDTO(wantedCategory.get());
        }else{
            logger.error("Error al buscar una categoria con el id : " + id);
            throw new BadRequestException("No existe Categoria con id " + id);
        }
    }

    @Override
    public Category saveCategory(CategoryDTO categoryDTO) throws BadRequestException {
        boolean IsIdAvailable = categoryDTO.getId() == null;
        boolean hasMandatoryFields = categoryDTO.getName() != null;
        hasMandatoryFields &= categoryDTO.getDescription() != null;
        hasMandatoryFields &= categoryDTO.getImageURL() != null;

        if(IsIdAvailable) {
            if (hasMandatoryFields) {
                return categoryRepository.save(convertDtoToClass(categoryDTO));
            } else {
                logger.error("Error al intentar guardar un producto con los datos proporcionados");
                throw new BadRequestException("No es posible guardar la categoria, faltan datos obligatorios.");
            }
        }else{
            throw new BadRequestException("No es posible guardar una categoria con Id ya asignado");
        }
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) throws BadRequestException {
        //casos malos
        if(categoryDTO == null){
            logger.error("Error al intentar modificar una categoría con datos nulos");
            throw new BadRequestException("No puede modificarse una categoría con datos nulos");
        }else{
            boolean categoryExists = categoryRepository.existsById(categoryDTO.getId());
            if(!categoryExists){
                throw new BadRequestException("No existe en el id " + categoryDTO.getId() + " en la base de datos");
            };
        }

        return convertClassToDTO(categoryRepository.save(convertDtoToClass(categoryDTO)));
    }

    @Override
    public boolean deleteCategoryById(Long id) throws BadRequestException {
        boolean categoryExists = categoryRepository.existsById(id);
        if(categoryExists){
            categoryRepository.deleteById(id);
            return true;
        }else{
            throw new BadRequestException("No existe el id " + id + " en la base de datos");
        }
    }

    @Override
    public List<CategoryDTO> searchAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
       logger.debug("Product count de la primer categoria "+ categoryList.get(0).getProductsCount());
        List<CategoryDTO> result = categoryList.stream().map(CategoryDTO::new).collect(Collectors.toList());
        return result;
    }

    @Override
    public CategoryDTO searchCategoryByName(String categoryName) throws BadRequestException {
        Category category = categoryRepository.searchCategoryByName(categoryName);
        if(category!=null){
            return convertClassToDTO(category);
        }else{
            throw new BadRequestException("No existe una categoría con el nombre "+ categoryName);
        }

    }

    //Private functions to make code shorter
    private CategoryDTO convertClassToDTO(Category category){
        return mapper.convertValue(category, CategoryDTO.class);
    }

    private Category convertDtoToClass(CategoryDTO categoryDTO){
        return mapper.convertValue(categoryDTO, Category.class);
    }

    private boolean idValido(CategoryDTO categoryDTO){
        Long id = categoryDTO.getId();
        boolean result = id != null;
        if(result){
            result = id > 0;
        }
        return result;
    }

}
