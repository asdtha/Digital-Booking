package grupo4.backend.service;

import grupo4.backend.dto.CharacteristicDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Characteristic;

import java.util.List;

public interface CharacteristicService {

    CharacteristicDTO searchCharacteristicById(Long id) throws BadRequestException;
    Characteristic saveCharacteristic(CharacteristicDTO characteristicDTO) throws BadRequestException;
    CharacteristicDTO updateCharacteristic(CharacteristicDTO characteristicDTO) throws BadRequestException;
    boolean deleteCharacteristicById(Long id) throws BadRequestException;
    List<CharacteristicDTO> searchAllCharacteristics();
    Characteristic searchCharacteristicByIdAsClass(Long id) throws BadRequestException;
}
