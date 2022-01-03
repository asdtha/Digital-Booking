package grupo4.backend.service;
import grupo4.backend.dto.UserDTO;
import grupo4.backend.exception.AuthenticationException;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.UserDB;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    UserDTO searchUserById(Long id) throws BadRequestException;
    UserDB searchUserByIdAsClass(Long id) throws BadRequestException;
    UserDB saveUser (UserDTO userDTO) throws BadRequestException, AuthenticationException;
    UserDB updateUserAsClass(UserDB userDB) throws BadRequestException;
    UserDTO updateUser(UserDTO userDTO) throws BadRequestException;
    boolean deleteUserById(Long id) throws BadRequestException;
    void updateResetPasswordToken(String token, String email ) throws AuthenticationException;
    void sendPasswordResetLinkEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException;
    UserDB getUserByResetPasswordToken (String resetPasswordToken);
    void updatePassword (UserDB user, String newPassword) throws AuthenticationException;
    void sendChangedPasswordSuccessfullEmail(UserDTO user) throws MessagingException, UnsupportedEncodingException;
    UserDB getUserByEmail (String email) throws BadRequestException;
    void sendRegisterConfirmationLinkEmail(String email, String registrationLink) throws MessagingException, UnsupportedEncodingException;
    List<UserDTO> searchAllUsers();
    UserDB getUserByRegistrationToken (String registrationToken);
    void sendRegisterSuccessfullEmail(UserDTO user) throws MessagingException, UnsupportedEncodingException;
    void setRegistrationTokenToNullAndSave(UserDB user);
    UserDB findByBearerJwt(String bearerJwt) throws BadRequestException;
    UserDTO findUserByMail(String email) throws BadRequestException;


}
