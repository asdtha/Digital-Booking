package grupo4.backend.service;
import grupo4.backend.model.UserDB;
import grupo4.backend.repository.IUserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class MyUserDetailsService implements UserDetailsService {

    final static Logger logger = Logger.getLogger(MyUserDetailsService.class);

    @Autowired
    private IUserRepository userRepository;

    //Find users from database
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDB foundUserDB = userRepository.findUserByEmail(email);
        logger.debug("Se encontró el usuario con email : " + email + ", con el rol: " + foundUserDB.getUserType().toString());
      List <GrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_" + foundUserDB.getUserType().toString()));


    UserDetails userDetails = new User(foundUserDB.getEmail(), foundUserDB.getPassword(), authorities);


    return userDetails;
    }
}
