package grupo4.backend.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String message){
        super(message);
    }
}
