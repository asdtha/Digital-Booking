package grupo4.backend.controller;
import grupo4.backend.dto.UserDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/API/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> searchUserById(@PathVariable Long id) throws BadRequestException {
        UserDTO userFound = userService.searchUserById(id);
        return ResponseEntity.ok(userFound);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser (@RequestBody UserDTO userDTO) throws BadRequestException{
        UserDTO userUpdated = userService.updateUser(userDTO);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws BadRequestException {
        if(id != null) {
            if(userService.deleteUserById(id)) {
                return ResponseEntity.ok("Se eliminó el usuario con id: " + id);
            } else {
                return new ResponseEntity<>("No existe un usuario con este id.", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("No fueron proporcionados datos.", HttpStatus.NO_CONTENT);
        }
    }
}
