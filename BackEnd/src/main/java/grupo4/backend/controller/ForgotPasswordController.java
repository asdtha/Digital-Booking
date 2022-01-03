package grupo4.backend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo4.backend.dto.UserDTO;
import grupo4.backend.exception.AuthenticationException;
import grupo4.backend.model.UserDB;
import grupo4.backend.service.UserService;
import grupo4.backend.util.GetUrlUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/API")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {


    //Dependency injections
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    ObjectMapper mapper;

    @PostMapping("/resetPassword")
    public String processRequestNewPasswordForm(HttpServletRequest request) throws AuthenticationException, MessagingException, UnsupportedEncodingException {
        String email = request.getParameter("email");
        String token = RandomString.make(45);
        userService.updateResetPasswordToken(token, email);

        //Message to user
        String message = "Se ha enviado un correo a: " + email;

        //Getting exact URL


        //String resetPasswordLink = "http://localhost:3000" + "/change-password?token=" + token;
        String resetPasswordLink = "http://digitalbooking.click" + "/change-password?token=" + token;

        //Sending email to user
        userService.sendPasswordResetLinkEmail(email, resetPasswordLink);

        return message;
    }

    @PostMapping("/changePassword")
    public String changePasswordForm(@RequestBody UserDTO user) throws AuthenticationException, MessagingException, UnsupportedEncodingException {
        UserDB foundUser = userService.getUserByResetPasswordToken(user.getResetPasswordToken());

        if (foundUser != null) {
            userService.updatePassword(foundUser, user.getPassword());
            UserDTO updatedUser= mapper.convertValue(foundUser, UserDTO.class);
            userService.sendChangedPasswordSuccessfullEmail(updatedUser);
        } else throw new AuthenticationException("Token inválido");

    return "Contraseña cambiada exitosamente";

    }
}
