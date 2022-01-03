package grupo4.backend.service;

import grupo4.backend.model.Image;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Image;

import java.util.List;

public interface ImageService {

    Image searchImageById(Long id) throws BadRequestException;
    Image saveImage(Image image, Long productId) throws BadRequestException;
    Image updateImage(Image image, Long productId) throws BadRequestException;
    boolean deleteImageById(Long id, Long productId) throws BadRequestException;
}
