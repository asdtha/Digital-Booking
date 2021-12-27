package grupo4.backend.model;

public class AuthenticationResponse {

    //Attributes
    private final String jwt;

    //Constructor
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    //Getter
    public String getJwt() {return jwt;}
}
