package grupo4.backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.dto.CharacteristicDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Characteristic;
import grupo4.backend.repository.CharacteristicRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacteristicServiceImp implements CharacteristicService{

    //Attributes
    private CharacteristicRepository characteristicRepository;
    private final ObjectMapper mapper;
    final static Logger logger = Logger.getLogger(CharacteristicServiceImp.class);

    //Constructor
    @Autowired
    public CharacteristicServiceImp (CharacteristicRepository characteristicRepository, ObjectMapper mapper) {
        this.characteristicRepository = characteristicRepository;
        this.mapper = mapper;
    }

    //Methods
    @Override
    public CharacteristicDTO searchCharacteristicById(Long id) throws BadRequestException {
        Optional<Characteristic> charac = characteristicRepository.findById(id);
        if(charac.isPresent()){
            return convertClassToDTO(charac.get());
        }else{
            logger.error("Error al buscar una característica con id : " + id);
            throw new BadRequestException("No existe Caracteristica con id "+id);
        }
    }

    @Override
    public Characteristic searchCharacteristicByIdAsClass(Long id) throws BadRequestException {
        Optional<Characteristic> characteristic = characteristicRepository.findById(id);
        if(characteristic.isPresent()){
            return characteristic.get();
        }else{
            throw new BadRequestException("No existe Caracteristica con id "+id);
        }
    }

    @Override
    public Characteristic saveCharacteristic(CharacteristicDTO characteristicDTO) throws BadRequestException {
        if(characteristicDTO == null){
            throw new BadRequestException("Faltan datos para cargar la Caracteristica");
        }

        return characteristicRepository.save(convertDtoToClass(characteristicDTO));
    }
    @Override
    public CharacteristicDTO updateCharacteristic(CharacteristicDTO characteristicDTO) throws BadRequestException {

        if(characteristicDTO == null){
            logger.error("Error al intentar modificar una característica con datos nulos ");
            throw new BadRequestException("No puede modificarse una característica con datos nulos");
        }else{
            boolean characteristicExists = characteristicRepository.existsById(characteristicDTO.getId());
            if(!characteristicExists){
                throw new BadRequestException("No existe caracteristica con id "+characteristicDTO.getId());
            }
        }

        return convertClassToDTO(characteristicRepository.save(convertDtoToClass(characteristicDTO)));
    }

    @Override
    public boolean deleteCharacteristicById(Long id) throws BadRequestException {
        boolean characteristicExists = characteristicRepository.existsById(id);
        if(characteristicExists){
            characteristicRepository.deleteById(id);
            return true;
        }else{
            throw new BadRequestException("No existe el id "+id);
        }
    }

    @Override
    public List<CharacteristicDTO> searchAllCharacteristics() {
        List<CharacteristicDTO> listCharacteristicsDTO;
        List<Characteristic> characteristicsList = characteristicRepository.findAll();
        return characteristicsList.stream().map(CharacteristicDTO::new).collect(Collectors.toList());
    }


    //Private functions to make code shorter
    private CharacteristicDTO convertClassToDTO(Characteristic characteristic){
        return mapper.convertValue(characteristic, CharacteristicDTO.class);
    }

    private Characteristic convertDtoToClass(CharacteristicDTO characteristicDTO){
        return mapper.convertValue(characteristicDTO, Characteristic.class);
    }

    private boolean idValido(CharacteristicDTO characteristicDTO){
        Long id = characteristicDTO.getId();
        boolean result = id != null;
        if(result){
            result = id > 0;
        }
        return result;
    }
}