package grupo4.backend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.dto.UserDTO;
import grupo4.backend.exception.AuthenticationException;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.AuthenticationRequest;
import grupo4.backend.model.AuthenticationResponse;
import grupo4.backend.model.UserDB;
import grupo4.backend.service.CategoryServiceImp;
import grupo4.backend.service.MyUserDetailsService;
import grupo4.backend.service.UserService;
import grupo4.backend.util.GetUrlUtil;
import grupo4.backend.util.JwtUtil;
import net.bytebuddy.utility.RandomString;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*")
@RequestMapping("/API/authenticate")
@RestController
public class UserAuthController {

    final static Logger logger = Logger.getLogger(UserAuthController .class);

    //Dependency injections
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    ObjectMapper mapper;

    //Endpoint where user will post credentials
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException bce) {
            throw new AuthenticationException("Incorrect credentials");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        String emailFound = authenticationRequest.getEmail();
        logger.debug("El mail encontrado es: "+ emailFound);
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        UserDB userInDataBase = null;
        try{
            userInDataBase = userService.getUserByEmail(emailFound);
            if(userInDataBase.getRegistrationToken() != null){throw new AuthenticationException("Es una cuenta válida, pero no está confirmado el mail.");}
            userInDataBase.setJwt(jwt);
            userService.updateUserAsClass(userInDataBase);
        }catch(BadRequestException e){
            throw new AuthenticationException("El mail no fue encontrado en la segunda busqueda, pero si en la primera. Rarisimo");
        }
        UserDTO loggedUser = new UserDTO(userInDataBase);
        loggedUser.setJwt(jwt);
        return ResponseEntity.ok(loggedUser);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDB>  processRequestRegisterForm(HttpServletRequest request, @RequestBody UserDTO userDTO) throws AuthenticationException, MessagingException, UnsupportedEncodingException, BadRequestException {
        String email = userDTO.getEmail();
        String token = RandomString.make(45);
        userDTO.setRegistrationToken(token);
        UserDB savedUser = userService.saveUser(userDTO);


        //Message to user
        String message = "Se ha enviado un correo a: " + email + ". Por favor, siga los pasos para completar su registro";

        //Getting exact URL
        String confirmRegistrationLink = GetUrlUtil.getSiteURL(request) + "/API/authenticate/confirmRegistration?token=" + token;

        //Sending email to user
        userService.sendRegisterConfirmationLinkEmail(email, confirmRegistrationLink);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/confirmRegistration")
    public void saveUser(HttpServletRequest request, HttpServletResponse response) throws BadRequestException, AuthenticationException, MessagingException, UnsupportedEncodingException {
        String token = request.getParameter("token");

        UserDB foundUser = userService.getUserByRegistrationToken(token);
        UserDTO foundUserDTO = mapper.convertValue(foundUser, UserDTO.class);
        logger.debug("confirm registration del usuario tiene el siguiente mail para enviarle el segundo mail: "+ foundUser.getEmail());
        logger.debug("confirm registration del usuarioDTO tiene el siguiente mail para enviarle el segundo mail: "+ foundUserDTO.getEmail());
        userService.sendRegisterSuccessfullEmail(foundUserDTO);
        userService.setRegistrationTokenToNullAndSave(foundUser);

        response.setHeader("Location", "http://localhost:3000/login");
        //response.setHeader("Location", "http://digitalbooking.click/login");
        response.setStatus(302);

    }
   }
