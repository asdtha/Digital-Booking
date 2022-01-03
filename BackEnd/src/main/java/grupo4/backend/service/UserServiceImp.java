package grupo4.backend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.dto.UserDTO;
import grupo4.backend.exception.AuthenticationException;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.UserDB;
import grupo4.backend.repository.IUserRepository;
import grupo4.backend.util.UserType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final IUserRepository userRepository;

    //Mapper to convert DTOs & java classes
    private final ObjectMapper mapper;

    //Error logging
    private final static Logger logger = Logger.getLogger(ProductServiceImp.class);

    //Dependency injection
    @Autowired
    public UserServiceImp(IUserRepository userRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    //Overriden methods
    @Override
    public UserDTO searchUserById(Long id) throws BadRequestException {
        return new UserDTO(searchUserByIdAsClass(id));
    }

    @Override
    public UserDB searchUserByIdAsClass(Long id) throws BadRequestException {
        Optional<UserDB> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            return optUser.get();
        }else{
            logger.error("Error: usuario inexistente.");
            throw new BadRequestException("No existe el Usuario con id "+ id);
        }
    }

    @Override
    public UserDB saveUser(UserDTO userDTO) throws BadRequestException, AuthenticationException {
        // errores en casos malos
        boolean isIdAvailable = userDTO.getId() == null;
        boolean hasMandatoryFields = userDTO.getFirstName() != null;
        hasMandatoryFields = userDTO.getLastName() != null;
        hasMandatoryFields = userDTO.getEmail() != null;
        hasMandatoryFields = userDTO.getPassword() != null;
        if(!isIdAvailable){
            logger.error("Error al intentar guardar un usuario con id existente");
            throw new BadRequestException("No es posible guardar un usuario con Id ya asignado");
        }else{
            if(!hasMandatoryFields){
                throw new BadRequestException("No es posible guardar el usuario, faltan completar campos obligatorios");
            }
        }
        //check if user email already exists
        if(userRepository.existsByEmail(userDTO.getEmail())){
            throw new AuthenticationException("Este email ya se encuentra registrado.");
        }
        if(userDTO.getUserType() == null){
            userDTO.setUserType(UserType.USER);
        }
        if(userDTO.getUserType() == UserType.USER) {//TODO checkear que no esta ahciendo dos veces lo mismo
            userDTO.setUserType(UserType.USER);
        }else if(userDTO.getUserType() == UserType.ADMIN){
            userDTO.setUserType(UserType.ADMIN);
        }else if(userDTO.getUserType() == UserType.SYSADMIN){
            userDTO.setUserType(UserType.SYSADMIN);
        }
        //We receive UserDTO with string and encrypt password to save on DB
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserDB userDB = convertUserToClass(userDTO);
        logger.debug(userDB);
        return userRepository.save(userDB);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) throws BadRequestException {
        // casos malos
        if(userDTO == null){
            throw new BadRequestException("No es posible modificar el usuario. Sus valores son nulos");
        }else{
            boolean existeEnLaBase = userRepository.existsById(userDTO.getId());
            if(!existeEnLaBase){
                throw new BadRequestException("No existe el usuario con id "+ userDTO.getId());
            }
        }
        UserDB userDB = convertUserToClass(userDTO);
        userRepository.save(userDB);
        logger.debug(userDB);
        return userDTO;
    }

    @Override
    public boolean deleteUserById(Long id) throws BadRequestException {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }else{
            throw new BadRequestException("No existe el usuario con id "+id);
        }

    }

    @Override
    public List<UserDTO> searchAllUsers() {
        List<UserDB> userDBList = this.userRepository.findAll();
        List<UserDTO> userDTOList = userDBList.stream().map(UserDTO::new).collect(Collectors.toList());
        return userDTOList;
    }

    //Methods to update password
    public void updateResetPasswordToken(String token, String email ) throws AuthenticationException {
    UserDB user = userRepository.findUserByEmail(email);
    if(user != null ) {
        user.setResetPasswordToken(token);
        userRepository.save(user);
    } else {
        throw new AuthenticationException("No se encontró un usuario con el email : " + email);
    }
    }

    public UserDB getUserByResetPasswordToken (String resetPasswordToken) {
        return userRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public UserDB getUserByEmail (String email) throws BadRequestException{
        UserDB result =  userRepository.findUserByEmail(email);
        if(result == null){throw new BadRequestException("No hay ningun usuario con ese mail en nuestra base de datos");}
        logger.debug("Se encontró el usuario : " + result);
        return result;
    }

    public void updatePassword (UserDB user, String newPassword) throws AuthenticationException {
        if (newPassword == null){
            throw new AuthenticationException("Debe ingresar una nueva contraseña");
        }
        if(newPassword.length()<6) {
            throw new AuthenticationException("La contraseña debe tener al menos 6 caracteres");
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        logger.debug("El usuario " + user + " realizó un cambio de contraseña.");
        userRepository.save(user);
    }

    public void sendPasswordResetLinkEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("dbookingDH@gmail.com", "Digital Booking Support");
        helper.setTo(email);
        String subject = "Éste es el link para recuperar tu contraseña: ";

        String content = "<p> Hola! </p>" + "<p>Hemos recibido una solicitud para recuperar tu contraseña. </p>" + "<p>Haz click en el siguiente link para generar una nueva contraseña: </p>" +
                "<p><a href=\"" + resetPasswordLink + "\"> Cambiar mi contraseña </a> </p>" + " <p>Si no has solicitado un cambio de contraseña, ignora este mensaje </p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        logger.debug("Se envió un mail a: " + email + " por solicitud de nueva contraseña.");
        mailSender.send(message);

    }

    //Methods to complete registration

    public void updateRegistrationToken(String token, String email ) throws AuthenticationException {
        UserDB user = userRepository.findUserByEmail(email);
        if(user != null ) {
            user.setRegistrationToken(token);
            userRepository.save(user);
        } else {
            throw new AuthenticationException("No se encontró un usuario con el email : " + email);
        }
    }

    public UserDB getUserByRegistrationToken (String registrationToken) {
        return userRepository.findByRegistrationToken(registrationToken);
    }

    public UserDB findByBearerJwt(String bearerJwt) throws BadRequestException{
        String[] dividedByWords = bearerJwt.split(" ");
        if(dividedByWords.length != 2){throw new BadRequestException("El JWT no fue pasado como Bearer Token");}
        if(!dividedByWords[0].equals("Bearer")){throw new BadRequestException("El JWT no fue pasado como Bearer Token, sino como: "+ dividedByWords[0]);}
        String cleanJwt = dividedByWords[1];
        return findByJwt(cleanJwt);
    }

    public UserDB findByJwt(String jwt) throws BadRequestException{
        UserDB userInDataBase = userRepository.findByJwt(jwt);
        logger.debug("Se encontró al usuario " + userInDataBase);
        if(userInDataBase == null){throw new BadRequestException("Ese token no tiene referencia.");}
        return userRepository.findByJwt(jwt);
    }

    public UserDB updateUserAsClass(UserDB user) throws BadRequestException{
        if(user.getId() == null){throw new BadRequestException("Imposible actualizar usuario con id nula");}
        return userRepository.save(user);
    }

    public void setRegistrationTokenToNullAndSave(UserDB user) {
        user.setRegistrationToken(null);
        userRepository.save(user);
    }

    public void sendRegisterConfirmationLinkEmail(String email, String registrationLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("dbookingDH@gmail.com", "Digital Booking Support");
        helper.setTo(email);
        String subject = "Completa el registro en DIGITAL BOOKING";
        String content = "<p> Hola! </p>" + "<p>Hemos recibido tu solicitud de registro en nuestro sitio DIGITAL BOOKING. </p>" + "Para completar el registro, haz click en el siguiente enlace: " +
                "<p><a href=\"" + registrationLink + "\"> Confirmar registro </a> </p>" + " <p>Si no has solicitado registrarte en nuestro sitio, ignora este mensaje </p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        logger.debug("Se envió un mail de confirmación de registro a: " + email);
        mailSender.send(message);

    }

    public void sendRegisterSuccessfullEmail(UserDTO user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("dbookingDH@gmail.com", "Digital Booking Support");
        helper.setTo(user.getEmail());
        String subject = "Registro exitoso!";
        String content = "<p> Hola, " + user.getFirstName() + "! </p>" + "<p> Te damos la bienvenida a DIGITAL BOOKING! </p>"+ "<p> Para comenzar a reservar, por favor, inicia sesión en el sitio. </p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        logger.debug("El usuario con email " + "ha completado el registro.");
        mailSender.send(message);
    }

    public void sendChangedPasswordSuccessfullEmail(UserDTO user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("dbookingDH@gmail.com", "Digital Booking Support");
        helper.setTo(user.getEmail());
        String subject = "Contraseña cambiada exitosamente!";
        String content = "<p> Hola, " + user.getFirstName() + "! </p>" + "<p> Tu contraseña ha sido cambiada exitosamente! </p>"+ "<p> Por favor, inicia sesión en el sitio nuevamente. </p>";
        helper.setSubject(subject);
        helper.setText(content, true);
        logger.debug("El usuario " + user.toString() + "ha cambiado su contraseña ");
        mailSender.send(message);
    }


    public UserDTO findUserByMail(String email) throws BadRequestException{
        if(email == null){
            logger.error("Error al intentar encontrar un usuario, el email es nulo ");
            throw new BadRequestException("El mail es nulo");}
        UserDB result = userRepository.findUserByEmail(email);
        return convertUserToDTO(result);
    };


    //Private functions to shorten code
    private UserDTO convertUserToDTO(UserDB userDB) throws BadRequestException{
        return mapper.convertValue(userDB, UserDTO.class);
    }


    private UserDB convertUserToClass(UserDTO userDTO){
        UserDB result = mapper.convertValue(userDTO, UserDB.class);
        return result;
    }



}
