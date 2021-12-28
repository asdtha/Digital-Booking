package grupo4.backend.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grupo4.backend.model.punctuation.Vote;
import grupo4.backend.util.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name="users")
public class UserDB implements UserDetails {

    // Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String resetPasswordToken;
    @Column
    private String registrationToken;
    @Column
    private String jwt;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @OneToMany(mappedBy= "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Favorite> favorites;
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Booking> myBookings;
    @OneToMany(mappedBy = "userWhoVoted", cascade = CascadeType.ALL)
    private List<Vote> voteList;

    // Constructor
    public UserDB(){}

    public void addBooking(Booking booking){myBookings.add(booking);}

    public boolean userAlreadyVotedOn(UserDB user, Long punctuationCounterId){
        for (Vote vote : voteList) {
            if (vote.getPunctuationCounter().getId() == punctuationCounterId){
                return true;
            }
        }
        return false;
    }

    // Getters and setters
    public Long getId() {return id;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    @Override
    public String getUsername() {return null;}
    public void setPassword(String password) {this.password = password;}
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public void setId(Long id) {this.id = id;}
    public String getResetPasswordToken() {return resetPasswordToken;}
    public void setResetPasswordToken(String resetPasswordToken) {this.resetPasswordToken = resetPasswordToken;}
    public List<Favorite> getFavorites() {return favorites;}
    public void setFavorites(List<Favorite> favorites) {this.favorites = favorites;}
    public String getRegistrationToken() {return registrationToken;}
    public void setRegistrationToken(String registerToken) {this.registrationToken = registerToken;}
    public List<Booking> getMyBookings() {
        return myBookings;
    }
    public void setMyBookings(List<Booking> myBookings) {
        this.myBookings = myBookings;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    public List<Vote> getVoteList() {
        return voteList;
    }
    public void setVoteList(List<Vote> voteList) {
        this.voteList = voteList;
    }

    //Methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userType.name());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {return false;}

    @Override
    public boolean isAccountNonLocked() {return false;}

    @Override
    public boolean isCredentialsNonExpired() {return false;}

    @Override
    public boolean isEnabled() {return false;}


    // to string
    @Override
    public String toString() {
        return "UserDB{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", myBookings = " + myBookings + '\'' +
                '}';
    }
}
