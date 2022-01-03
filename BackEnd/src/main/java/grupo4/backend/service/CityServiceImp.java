package grupo4.backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.dto.CityDTO;
import grupo4.backend.dto.ProductDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.City;
import grupo4.backend.repository.CityRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CityServiceImp implements CityService {

    private final CityRepository cityRepository;
    private final ObjectMapper mapper;
    final static Logger logger = Logger.getLogger(CityServiceImp.class);

    @Autowired
    public CityServiceImp(CityRepository cityRepository, ObjectMapper mapper) {
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }


    @Override
    public CityDTO searchCityById(Long id) throws BadRequestException {
        Optional<City> city = cityRepository.findById(id);
        if(city.isPresent()){
            return convertCityToDTO(city.get());
        }else{
            throw new BadRequestException("No existe una ciudad con id " + id);
        }
    }

    @Override
    public City saveCity(CityDTO cityDTO) throws BadRequestException {
       boolean isIdAvailable = cityDTO.getId() == null;
        boolean hasMandatoryFields = cityDTO.getName() != null;
        hasMandatoryFields &= cityDTO.getCountry_name() != null;

        if (!isIdAvailable) {
            logger.error("Error al guardar una ciudad con id asignado");
            throw new BadRequestException("No es posible guardar la ciudad, porque el ID ya existe");
        } else {
            if (!hasMandatoryFields) {
                logger.error("Faltan datos para cargar la ciudad");
                throw new BadRequestException("Faltan datos para cargar la ciudad");
            }
        }
        return cityRepository.save(convertCityToClass(cityDTO));
    }

    @Override
    public CityDTO updateCity(CityDTO cityDTO) throws BadRequestException {
        //casos malos
        if(cityDTO == null){
            throw new BadRequestException("No es posible modificar la ciudad. No se proporcionaron datos.");
        }else{
            boolean existeEnLaBase = cityRepository.existsById(cityDTO.getId());
            if(!existeEnLaBase){
                throw new BadRequestException("No existe la ciudad con id " + cityDTO.getId() + " en la base de datos");
            };
        }

        return convertCityToDTO(cityRepository.save(convertCityToClass(cityDTO)));
    }

    @Override
    public boolean deleteCityById(Long id) throws BadRequestException {
        boolean cityExists = cityRepository.existsById(id);
        if(cityExists){
            cityRepository.deleteById(id);
            return true;
        }else{
            throw new BadRequestException("No existe la ciudad con id ");
        }
    }

    @Override
    public List<CityDTO> searchAllCities() {
        List <CityDTO> response;
        List <City> citiesList = this.cityRepository.findAll();
        response= citiesList.stream().map(CityDTO::new).collect(Collectors.toList());

        return response;
    }

    @Override
    public CityDTO searchCityByName(String cityName) throws BadRequestException {
        City city = cityRepository.searchCityByName(cityName);
        if(city!=null){
            return convertCityToDTO(city);
        }else{
            throw new BadRequestException("No existe una ciudad con el nombre "+ cityName);
        }

    }

    //funciones privadas para acortar texto
    private CityDTO convertCityToDTO(City city){
        return mapper.convertValue(city, CityDTO.class);
    }

    private City convertCityToClass(CityDTO cityToDTO){
        return mapper.convertValue(cityToDTO, City.class);
    }

    private boolean hasValidId(CityDTO cityToDTO){
        Long id = cityToDTO.getId();
        boolean result = id != null;
        if(result){
            result = id > 0;
        }
        return result;
    }

}