package grupo4.backend.controller;
import grupo4.backend.dto.FavoriteDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Favorite;
import grupo4.backend.model.UserDB;
import grupo4.backend.service.FavoriteService;
import grupo4.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/API/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;


    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/allFavorites")
    public ResponseEntity<List<FavoriteDTO>> searchAllFavorites(){
        List<FavoriteDTO> favoritesList = favoriteService.searchAllFavorites();
        return ResponseEntity.ok(favoritesList);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{productId}")
    public ResponseEntity saveFavorite(@PathVariable Long productId, HttpServletRequest request) throws BadRequestException {
        String bearerJwt = request.getHeader("Authorization");
        UserDB foundUser = userService.findByBearerJwt(bearerJwt);//esto es lo único que checkea validez del token desde back end.
        Favorite favorite = new Favorite();
        favorite.setUser(foundUser);
        FavoriteDTO savedFavorite = favoriteService.saveFavorite(favorite, productId);
        return ResponseEntity.ok(savedFavorite);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFavorite(@PathVariable Long id) throws BadRequestException {
        //TODO Preguntar a tech lead si hace falta el temita de seguridad de chequear si el usuario que pide eliminar sea el mismo que realizó el Favorito, .
        if(id != null) {
            if(favoriteService.deleteFavoriteById(id)) {
                return ResponseEntity.ok("Se eliminó el favorito con id: " + id);
            } else {
                return new ResponseEntity<>("No existe un favorito con este id.", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("No fueron proporcionados datos.", HttpStatus.NO_CONTENT);
        }
    }

}
