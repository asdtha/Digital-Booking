package grupo4.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.model.Image;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.City;
import grupo4.backend.model.Image;
import grupo4.backend.model.Product;
import grupo4.backend.repository.IProductRepository;
import grupo4.backend.repository.ImageRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImp implements ImageService {
    private final ImageRepository imageRepository;
    private final IProductRepository productRepository;
    private final ObjectMapper mapper;

    final static Logger logger = Logger.getLogger(ImageServiceImp.class);


    @Autowired
    public ImageServiceImp(ImageRepository imageRepository, IProductRepository productRepository, ObjectMapper mapper) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public Image searchImageById(Long id) throws BadRequestException {
        Optional<Image> image = imageRepository.findById(id);
        if(image.isPresent()){
            return image.get();
        }else{
            logger.error("Error al intentar buscar una imagen con id : " + id);
            throw new BadRequestException("No existe una imagen con id " + id);
        }
    }


    @Override
    public Image saveImage(Image image, Long productId) throws BadRequestException {
        boolean idIsAvailable = image.getId() == null;

        Optional<Product> optProduct = productRepository.findById(productId);
        boolean hasMandatoryFields = image.getUrl() != null;

        if(!idIsAvailable){
            logger.error("Error al intentar guardar una imagen con ID existente.");
            throw new BadRequestException("No es posible guardar una imagen con Id ya asignado");
        }else if(optProduct.isEmpty()){
            logger.error("Error: no existe el producto al que se intenta agregar la imagen " + image);
            throw new BadRequestException("No existe ese producto en nuestra base de datos.");
        }else if(!hasMandatoryFields){
            throw new BadRequestException("No tiene los campos requeridos: imagen URL");
        }else{
            image.setProduct(optProduct.get());
            return imageRepository.save(image);
        }
    }

    @Override
    public Image updateImage(Image image, Long productId) throws BadRequestException {
        if(image.getId()== null){throw new BadRequestException("no se puede modificar una imagen sin id.");}
        Image inBase = imageRepository.getById(image.getId());
        boolean productDidntChange = inBase.getProduct().getId().equals(productId);
        boolean hasMandatoryFields = image.getUrl() != null;

        if(!productDidntChange){
            logger.error("Error al intentar actualizar la imagen " + image + ", no pertenece al producto indicado.");
            throw new BadRequestException("Dicha imagen no pertenece al producto indicado.");
        }else if(!hasMandatoryFields){
            throw new BadRequestException("La modificacion no tiene los campos requeridos: imagen URL");
        }
        //asigno
        inBase.setTitle(image.getTitle());
        inBase.setUrl(image.getUrl());
        return imageRepository.save(inBase);

    }

    @Override
    public boolean deleteImageById(Long id, Long productId) throws BadRequestException {
        boolean imageExists = imageRepository.existsById(id);
        Optional<Product> optProduct = productRepository.findById(productId);
        boolean productIsValid = optProduct.isPresent();
        if(!imageExists){
            throw new BadRequestException("No existe ninguna imagen con Id "+ id + " en nuestra base de datos.");
        }
        if(!productIsValid){
            throw new BadRequestException("no existe dicho producto con id "+ productId +" en nuestra base de datos");
        }
        Image oldImage = imageRepository.findById(id).get();
        Product product = optProduct.get();
        productIsValid &= product.getId().equals(oldImage.getProduct().getId());
        productIsValid &= oldImage.getProduct().getId() != null;

        if(productIsValid){
            imageRepository.deleteById(id);
            logger.debug("Se eliminó correctamente la imagen con id: " + id );
            return true;

        }else{
                throw new BadRequestException("La imagen no corresponde con el producto indicado.");
        }
    }
    /*

    private Image convertImageTo(Image i){
        Image result = mapper.convertValue(i, Image.class);
        result.setProductId(i.getProduct().getId());
        return result;

    }

    private Image convertToImage(Image i){
        Image result = mapper.convertValue(i, Image.class);
        Product p = new Product();
        p.setId(i.getProductId());
        try{
            //p = productRepository.searchProductById(i.getProductId());
        }catch(Exception e){

        }
        result.setProduct(p);
        return result;
    }
    */
}
