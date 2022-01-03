package grupo4.backend.controller;

import grupo4.backend.dto.CityDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.City;
import grupo4.backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/cities")
@CrossOrigin("*")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/{id}")
    public ResponseEntity<CityDTO> searchCityById(@PathVariable Long id) throws BadRequestException {
        CityDTO foundCity = cityService.searchCityById(id);
        return ResponseEntity.ok(foundCity);
    }

    @GetMapping("/allCities")
    public ResponseEntity<List<CityDTO>> searchAllCities(){
        List<CityDTO> citiesList = cityService.searchAllCities();
        return ResponseEntity.ok(citiesList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity saveCity(@RequestBody CityDTO cityDTO) throws BadRequestException {
        City savedCity = cityService.saveCity(cityDTO);
        return ResponseEntity.ok(savedCity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<CityDTO> updateCity(@RequestBody CityDTO cityDTO) throws BadRequestException{
        CityDTO updatedCity = cityService.updateCity(cityDTO);
        return ResponseEntity.ok(updatedCity);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id) throws BadRequestException {
        if(id != null) {
            if(cityService.deleteCityById(id)) {
                return ResponseEntity.ok("Se eliminó la ciudad con id: " + id);
            } else {
                return new ResponseEntity<>("No existe una ciudad con este id.", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("No fueron proporcionados datos.", HttpStatus.NO_CONTENT);
        }
    }
}
