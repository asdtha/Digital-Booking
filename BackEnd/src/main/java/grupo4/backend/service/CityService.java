package grupo4.backend.service;
import grupo4.backend.dto.CityDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.City;
import java.util.List;

public interface CityService {

    CityDTO searchCityById(Long id) throws BadRequestException;
    City saveCity(CityDTO city) throws BadRequestException;
    CityDTO updateCity(CityDTO city) throws BadRequestException;
    boolean deleteCityById(Long id) throws BadRequestException;
    List<CityDTO> searchAllCities();
    CityDTO searchCityByName (String cityName) throws BadRequestException;

}