package grupo4.backend.repository;
import grupo4.backend.model.UserDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserDB,Long> {

    UserDB findUserByEmail (String email);
    Boolean existsByEmail(String email);
    UserDB findByResetPasswordToken(String token);
    UserDB findByRegistrationToken(String token);
    UserDB findByJwt(String jwt);
}
