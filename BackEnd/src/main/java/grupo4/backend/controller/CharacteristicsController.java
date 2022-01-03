package grupo4.backend.controller;


import grupo4.backend.dto.CharacteristicDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Characteristic;
import grupo4.backend.service.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/characteristics")
@CrossOrigin("*")
public class CharacteristicsController {

    @Autowired
    private CharacteristicService characteristicService;

    @GetMapping("/allCharacteristics")
    public ResponseEntity<List<CharacteristicDTO>> searchAllCities(){
        List<CharacteristicDTO> citiesList = characteristicService.searchAllCharacteristics();
        return ResponseEntity.ok(citiesList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity saveCharacteristic(@RequestBody CharacteristicDTO characteristicDTO) throws BadRequestException {
        Characteristic savedCharacteristic = characteristicService.saveCharacteristic(characteristicDTO);
        return ResponseEntity.ok(savedCharacteristic);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<CharacteristicDTO> updateCharacteristic(@RequestBody CharacteristicDTO characteristicDTO) throws BadRequestException{
        CharacteristicDTO updatedCharacteristic = characteristicService.updateCharacteristic(characteristicDTO);
        return ResponseEntity.ok(updatedCharacteristic);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCharacteristic(@PathVariable Long id) throws BadRequestException {
        if(id != null) {
            if(characteristicService.deleteCharacteristicById(id)) {
                return ResponseEntity.ok("Se eliminó la caracteristica con id: " + id);
            } else {
                return new ResponseEntity<>("No existe una caracteristica con este id.", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("No fueron proporcionados datos.", HttpStatus.NO_CONTENT);
        }
    }
}
