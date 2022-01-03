package grupo4.backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Favorite;
import grupo4.backend.dto.FavoriteDTO;
import grupo4.backend.model.Product;
import grupo4.backend.repository.IFavoriteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImp implements FavoriteService{

    private final IFavoriteRepository favoriteRepository;
    private final ObjectMapper mapper;
    private final ProductService productService;
    final static Logger logger = Logger.getLogger(FavoriteServiceImp.class);

    @Autowired
    public FavoriteServiceImp(IFavoriteRepository favoriteRepository, ObjectMapper mapper, ProductService productService) {
        this.favoriteRepository = favoriteRepository;
        this.mapper = mapper;
        this.productService = productService;
    }

    @Override
    public FavoriteDTO saveFavorite(Favorite favorite, Long productId) throws BadRequestException {
        boolean isIdAvailable = favorite.getId() == null;
        boolean hasMandatoryFields = productId != null;

        if (!isIdAvailable) {
            logger.error("Error al guardar el favorito con ID existente");
            throw new BadRequestException("No es posible guardar el favorito, porque el ID ya existe");
        } else {
            if (!hasMandatoryFields) {
                throw new BadRequestException("No es posible guardar el favorito, faltan datos obligatorios.");
            }
        }
        boolean alreadyExist = 0 < favoriteRepository.existByUserAndProductId(productId, favorite.getUser().getId());
        if(alreadyExist){throw new BadRequestException("Cant create a duplicate favorite");}
        Product product = productService.searchProductByIdAsClass(productId);
        favorite.setProduct(product);
        return new FavoriteDTO(favoriteRepository.save(favorite));
    }

    @Override
    public boolean deleteFavoriteById(Long id) throws BadRequestException {
        boolean cityExists = favoriteRepository.existsById(id);
        if(cityExists){
            favoriteRepository.deleteById(id);
            return true;
        }else{
            logger.error("Error al intentar borrar un favorito con id nulo");
            throw new BadRequestException("No existe el favorito con id ");
        }
    }

    @Override
    public List<FavoriteDTO> searchAllFavorites() {
        List <FavoriteDTO> response;
        List <Favorite> favoritesList = this.favoriteRepository.findAll();
        response= favoritesList.stream().map(FavoriteDTO::new).collect(Collectors.toList());

        return response;
    }


    @Override
    public void deleteAllFavoritesWithProductID(Long id) throws BadRequestException {
        if(id == null){
            logger.error("Error al intentar borrar un favorito con id de producto nulo.");
            throw new BadRequestException("El id es nulo.");}
        List<Long> listOfIdFromThatProduct = favoriteRepository.findAllByIdWithProductId(id);
        for (Long idFav: listOfIdFromThatProduct
             ) {
            deleteFavoriteById(idFav);
        }
    }


    //private functions to shorten code
    private FavoriteDTO convertFavoriteToDTO(Favorite favorite){
        return mapper.convertValue(favorite, FavoriteDTO.class);
    }

    private Favorite  convertFavoriteToClass(FavoriteDTO favoriteToDTO){
        return mapper.convertValue(favoriteToDTO, Favorite.class);
    }

    private boolean hasValidId(FavoriteDTO favoriteToDTO){
        Long id = favoriteToDTO.getId();
        boolean result = id != null;
        if(result){
            result = id > 0;
        }
        return result;
    }


}
