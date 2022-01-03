package grupo4.backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.dto.CategoryDTO;
import grupo4.backend.dto.CityDTO;
import grupo4.backend.dto.ProductDTO;
import grupo4.backend.model.punctuation.PunctuationCounter;
import grupo4.backend.repository.PunctuationCounterRepository;
import grupo4.backend.util.ProductFilter;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.*;
import grupo4.backend.repository.IProductRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


import java.util.stream.Collectors;


@Service
public class ProductServiceImp implements ProductService {

    private final IProductRepository productRepository;
    private final ObjectMapper mapper;
    private final static Logger logger = Logger.getLogger(ProductServiceImp.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CharacteristicService characteristicService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private PunctuationCounterRepository pcr;


    @Autowired
    public ProductServiceImp(IProductRepository productRepository, ObjectMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO searchProductById(Long id) throws BadRequestException {
         Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isPresent()){
            Product product = optProduct.get();
            ProductDTO result = new ProductDTO(product);
            setCityAndCategoryFromProduct(result, product);
            return result;

        }else{
            throw new BadRequestException("No existe un producto con id " + id);
        }
    }

    @Override
    public Product searchProductByIdAsClass(Long id) throws BadRequestException{
        Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isPresent()){
            Product product = optProduct.get();
            return product;
        }else{
            throw new BadRequestException("No existe un producto con id " + id);
        }
    }

    @Override
    public Product saveProduct(ProductDTO productDTO) throws BadRequestException {
        //errores en casos malos
        boolean isIdAvailable = productDTO.getId() == null;
        boolean hasMandatoryFields = productDTO.getName() != null;
        hasMandatoryFields &= productDTO.getDescription() != null;
        hasMandatoryFields &= productDTO.getLatitude() != null && productDTO.getLongitude() != null;
        hasMandatoryFields &= productDTO.getEarliestCheckInHour() != null && productDTO.getLatestCheckInHour() != null;
        if (!isIdAvailable) {
            throw new BadRequestException("No es posible guardar un producto con Id ya asignado");
        }
        if (!hasMandatoryFields) {
                throw new BadRequestException("No es posible guardar el producto. Faltan datos obligatorios.");
        }else{
            boolean checkInHoursAreValid = productDTO.getEarliestCheckInHour() >= 0;
            checkInHoursAreValid &= productDTO.getEarliestCheckInHour() < productDTO.getLatestCheckInHour();
            checkInHoursAreValid &= productDTO.getLatestCheckInHour() <= 24;
            if(!checkInHoursAreValid){throw new BadRequestException("Hora de entrada y hora de salida no son válidas.");}
        }
        Product changeAndSave = convertProductToClass(productDTO);
        changeAndSave.createAndSetNewPuntuationCounter();
        sanitizeCharacteristics(changeAndSave);
        setProductInChildImages(changeAndSave);
        setIdOfImagesToNull(changeAndSave);
        Product saved = productRepository.save(changeAndSave);
        setProductInChildImages(saved);
        return saved;
    }



    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) throws BadRequestException {
        //casos malos
        if(productDTO == null){
            throw new BadRequestException("No es posible modificar el producto. No se proporcionaron datos.");
        }else{
            boolean existeEnLaBase = productRepository.existsById(productDTO.getId());
            if(!existeEnLaBase){
                throw new BadRequestException("No existe el producto con id " + productDTO.getId() + " en la base de datos");
            };
        }
        boolean isIdAvailable = productDTO.getId() == null;
        boolean hasMandatoryFields = productDTO.getName() != null;
        hasMandatoryFields &= productDTO.getDescription() != null;
        hasMandatoryFields &= productDTO.getLatitude() != null && productDTO.getLongitude() != null;
        hasMandatoryFields &= productDTO.getEarliestCheckInHour() != null && productDTO.getLatestCheckInHour() != null;
        if (!hasMandatoryFields) {
            throw new BadRequestException("No es posible guardar el producto. Faltan datos obligatorios.");
        }else{
            boolean checkInHoursAreValid = productDTO.getEarliestCheckInHour() >= 0;
            checkInHoursAreValid &= productDTO.getEarliestCheckInHour() < productDTO.getLatestCheckInHour();
            checkInHoursAreValid &= productDTO.getLatestCheckInHour() <= 24;
            if(!checkInHoursAreValid){throw new BadRequestException("Hora de entrada y hora de salida no son válidas.");}
        }

        Product modifyAndSave = convertProductToClass(productDTO);
        sanitizeCharacteristics(modifyAndSave);
        modifyAndSave.setImages(null);
        productRepository.save(modifyAndSave);
        return searchProductById(productDTO.getId());
        /*Product updated = productRepository.save(modifyAndSave);
        setImagesFromDataBase(updated);
        return updated;

         */

    }

    @Override
    public boolean deleteProductById(Long id) throws BadRequestException {
        boolean productExists = productRepository.existsById(id);
        if(productExists){
            favoriteService.deleteAllFavoritesWithProductID(id);
            productRepository.deleteById(id);
            return true;
        }else{
            throw new BadRequestException("No existe el producto con id " + id + " en la base de datos");
        }
    }

    @Override
    public List<ProductDTO> searchAllProducts() {
        List <ProductDTO> response;
        List <Product> productList = this.productRepository.findAll();
        response= productList.stream().map(ProductDTO::new).collect(Collectors.toList());
        return response;
    }

    @Override
     public List <ProductDTO> getProductsByCity(String cityName) throws BadRequestException {

        CityDTO cityExists = cityService.searchCityByName(cityName);
        if(cityExists==null) {
            throw new BadRequestException("No existe una ciudad con ese nombre");
        }
        List <Product> matchingProducts = this.productRepository.getProductsByCity(cityName);
        return convertToDTOList(matchingProducts);
    }

    @Override
    public List <ProductDTO> getProductsByCategory(String categoryName) throws BadRequestException {

        CategoryDTO categoryExists= categoryService.searchCategoryByName(categoryName);
        if(categoryExists==null) {
            throw new BadRequestException("No existe una ciudad con ese nombre");
        }
        List <Product> matchingProducts = this.productRepository.getProductsByCategory(categoryName);
        return convertToDTOList(matchingProducts);
    }

    @Override
    public List<ProductDTO> getProductsByCityAndDates(ProductFilter filter) throws BadRequestException{
        //errores
        boolean noNullData = filter.getCheckInDate() != null && filter.getCheckOutDate() != null && filter.getCityId() != null;
        if(!noNullData){throw new BadRequestException("El filter viene con data null");}
        boolean datesAreInOrder = filter.getCheckOutDate().isAfter(filter.getCheckInDate());
        boolean oldCheckIn = LocalDate.now().isAfter(filter.getCheckInDate());
        if(!datesAreInOrder){throw new BadRequestException("Las fechas estan en orden incorrecto o son iguales");}
        if(oldCheckIn){throw new BadRequestException("El Check In no puede estar en el pasado.");}
        cityService.searchCityById(filter.getCityId());     //si no existe el id, tira un badRequest
        List<Product> results = productRepository.getProductsByCityAndDates(filter.getCityId(), filter.getCheckInDate(), filter.getCheckOutDate());
        return convertToDTOList(results);
    };

    @Override
    public List<ProductDTO> getProductsByFilter(ProductFilter filter) throws BadRequestException{
        //preparense para el unico caso de "exclusive or", o XOR, que veremos en nuestra vida.
        boolean oneDateIsNullTheOtherIsValid = filter.getCheckInDate() == null ^ filter.getCheckOutDate() == null;
        if(oneDateIsNullTheOtherIsValid){throw new BadRequestException("Una fecha es válida, pero la otra no.");}
        boolean oneIntIsNullTheOtherIsValid = filter.getLimit() == null ^ filter.getOffset() == null;
        if(oneIntIsNullTheOtherIsValid){throw new BadRequestException("Un Integer es valido, pero el otro no");}
        boolean datesExist = filter.getCheckInDate() != null && filter.getCheckOutDate() != null;
        if(datesExist){
            boolean datesAreInOrder = filter.getCheckOutDate().isAfter(filter.getCheckInDate());
            boolean oldCheckIn = LocalDate.now().isAfter(filter.getCheckInDate());
            if(!datesAreInOrder){throw new BadRequestException("Las fechas estan en orden incorrecto o son iguales");}
            if(oldCheckIn){throw new BadRequestException("El Check In no puede estar en el pasado.");}
        }
        boolean hasOffsetAndLimit = filter.getLimit() != null && filter.getOffset() != null;
        if(!hasOffsetAndLimit){
            List<Product> results = productRepository.getProductsByFilter(filter.getCategoryId(), filter.getCityId(), filter.getCheckInDate(), filter.getCheckOutDate());
            return convertToDTOList(results);
        }else {
            List<Product> results = productRepository.getProductsByFilterWithOffset(filter.getCategoryId(), filter.getCityId(), filter.getCheckInDate(), filter.getCheckOutDate(), filter.getOffset(), filter.getLimit());
            return convertToDTOList(results);
        }
    }

        //FUNCIONES PRIVADAS PARA ACORTAR TEXTO
        // *************************************
    private ProductDTO convertProductToDTO(Product product) throws BadRequestException{
        return mapper.convertValue(product, ProductDTO.class);
    }


    private void setCityAndCategoryFromProduct( ProductDTO dto, Product product) throws BadRequestException {
        Category cat = mapper.convertValue( categoryService.searchCategoryById(product.getCategory().getId()), Category.class);
        City city = mapper.convertValue(cityService.searchCityById((product.getCity().getId())), City.class);
        dto.setCity(city);
        dto.setCategory(cat);
    }

    private Product convertProductToClass(ProductDTO productToDTO){
        Product result = mapper.convertValue(productToDTO, Product.class);
        return result;
    }


    //traigo las caracteristicas de base de datos, para que no se guarde ninguna nueva, ni modifique las viejas
    private void sanitizeCharacteristics(Product product){
        if(product.getCharacteristics() != null) {
            List<Characteristic> listOfValidChar = new LinkedList<Characteristic>();
            List<Characteristic> uncleanCharacteristics = product.getCharacteristics();
            for (Characteristic unclean : uncleanCharacteristics) {
                try{
                    //Characteristic buscarEnBase = mapper.convertValue(characteristicService.searchCharacteristicById(unclean.getId()), Characteristic.class);
                    Characteristic buscarEnBase = characteristicService.searchCharacteristicByIdAsClass(unclean.getId());
                if (buscarEnBase != null) {
                    listOfValidChar.add(buscarEnBase);
                }
                }catch(Exception e){
                    //do nothing, users cant create a new characteristic.
                };
            }
            product.setCharacteristics(listOfValidChar);
        }
    }



    //Dentro de images, product no puede ser nulo, así que hay que setearlo.
    private void setProductInChildImages(Product product){
        if(product.getImages()!=null){
            for (Image image : product.getImages()){
                if(image.getId()== null) {
                    image.setProduct(product);
                }
            }
        }
    }

    private void setImagesFromDataBase(Product product){
        Optional<Product> optEnBase = productRepository.findById(product.getId());

        if(optEnBase.isPresent()) {
            product.setImages(optEnBase.get().getImages());
        }
    };


    //borro el id de las imagenes
    private void setIdOfImagesToNull(Product product){
        if(product.getImages()!=null){
            for (Image image : product.getImages()){
                image.setId(null);
            }
        }
    }

    private boolean hasValidId(ProductDTO productToDTO){
        Long id = productToDTO.getId();
        boolean result = id != null;
        if(result){
            result = id > 0;
        }
        return result;
    }

    private List<ProductDTO> convertToDTOList(List<Product> list){
        return list.stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    private PunctuationCounter getPuntuationCounter(Long puntuationId) throws BadRequestException{
        Optional<PunctuationCounter> optPC = pcr.findById(puntuationId);
        if(optPC.isEmpty()){
            throw new BadRequestException("El puntuation Counter Id no existe en nuestra base de datos.");
        }else{
            return optPC.get();
        }
    }

}
