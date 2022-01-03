package grupo4.backend.service;
import grupo4.backend.dto.FavoriteDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Favorite;

import java.util.List;

public interface FavoriteService {
   // FavoriteDTO searchFavoriteById(Long id) throws BadRequestException;
    FavoriteDTO saveFavorite(Favorite favorite, Long productID) throws BadRequestException;
    boolean deleteFavoriteById(Long id) throws BadRequestException;
    List<FavoriteDTO> searchAllFavorites();
    void deleteAllFavoritesWithProductID(Long id) throws BadRequestException;
}
