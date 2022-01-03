package grupo4.backend.controller;

import grupo4.backend.model.Image;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("API/images")
@CrossOrigin(origins = "*")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<Image> searchImageById(@PathVariable Long id) throws BadRequestException {
        Image foundImage = imageService.searchImageById(id);
        return ResponseEntity.ok(foundImage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{productId}")
    public ResponseEntity saveImage(@RequestBody Image image, @PathVariable Long productId) throws BadRequestException {
        Image savedImage = imageService.saveImage(image, productId);
        return ResponseEntity.ok(savedImage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<Image> updateImage(@RequestBody Image image, @PathVariable Long productId) throws BadRequestException{
        Image updatedImage = imageService.updateImage(image, productId);
        return ResponseEntity.ok(updatedImage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/{productId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id, @PathVariable Long productId) throws BadRequestException {
        if(id != null && productId != null) {
            if(imageService.deleteImageById(id, productId)) {
                return ResponseEntity.ok("Se eliminó la imagen con id: " + id);
            } else {
                return new ResponseEntity<>("No existe una imagen con este id.", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("ID es null, no hay operación posible", HttpStatus.NO_CONTENT);
        }
    }
}
