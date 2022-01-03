package grupo4.backend.dto;
import grupo4.backend.model.*;
import grupo4.backend.model.punctuation.Vote;
import grupo4.backend.util.UserType;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO implements Serializable {

    // Attributes
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserType userType;
    private String resetPasswordToken;
    private List<FavoriteDTO> favoriteList;
    private List<VoteDTO> voteList;
    private String jwt;
    private String registrationToken;

    // Constructors
    public UserDTO(){

    }
    public UserDTO(UserDB userDB) {
        this.id = userDB.getId();
        this.firstName = userDB.getFirstName();
        this.lastName = userDB.getLastName();
        this.email = userDB.getEmail();
        this.userType = userDB.getUserType();
        this.favoriteList = convertFavoriteListOfDTO(userDB.getFavorites());
        this.voteList = convertVoteListToDTO(userDB.getVoteList());
        this.jwt = userDB.getJwt();
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String password, grupo4.backend.util.UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
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
    public String getRegistrationToken() {
        return registrationToken;
    }
    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    public List<FavoriteDTO> getFavoriteList() {
        return favoriteList;
    }
    public void setFavoriteList(List<FavoriteDTO> favoriteList) {
        this.favoriteList = favoriteList;
    }
    private List<FavoriteDTO> convertFavoriteListOfDTO(List<Favorite> favorites){return favorites.stream().map(FavoriteDTO::new).collect(Collectors.toList());}
    private List<VoteDTO> convertVoteListToDTO(List<Vote> votes){return votes.stream().map(VoteDTO::new).collect(Collectors.toList());}

    //To String
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
