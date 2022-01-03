package grupo4.backend.controller;


import grupo4.backend.dto.VoteDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Booking;
import grupo4.backend.model.UserDB;
import grupo4.backend.model.punctuation.Vote;
import grupo4.backend.service.UserService;
import grupo4.backend.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/API/votes")
public class VoteController {

    @Autowired
    private UserService userService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/{id}")
    public ResponseEntity<VoteDTO> searchVoteById(@PathVariable Long id, HttpServletRequest request) throws BadRequestException {
        String bearerJwt = request.getHeader("Authorization");
        UserDB foundUser = userService.findByBearerJwt(bearerJwt);
        VoteDTO dtoEncontrado = voteService.searchVoteById(id);
        return ResponseEntity.ok(dtoEncontrado);
    }

    @PostMapping
    public ResponseEntity<Vote> saveVote(@RequestBody VoteDTO voteDTO, HttpServletRequest request) throws BadRequestException {
        String bearerJwt = request.getHeader("Authorization");
        UserDB foundUser = userService.findByBearerJwt(bearerJwt);
        Vote savedVote = voteService.saveVote(voteDTO, foundUser);
        return new ResponseEntity<Vote>( savedVote, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<VoteDTO> updateVote(@RequestBody VoteDTO voteDTO) throws BadRequestException{
        VoteDTO updatedVote = voteService.updateVote(voteDTO);
        return ResponseEntity.ok(updatedVote);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVoteById(@PathVariable Long id) throws BadRequestException {
        if(id != null) {
            if(voteService.deleteVoteById(id)) {
                return ResponseEntity.ok("Fue eliminada el voto con id: " + id);
            } else {
                return new ResponseEntity<>("No existe voto con id "+ id, HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("No hay data en el id", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("fromProduct/{productId}")
    public ResponseEntity<List<VoteDTO>> votesByProductId(@PathVariable Long productId){
        List<VoteDTO> results = voteService.getVotesFromProductWithId(productId);
        return ResponseEntity.ok(results);
    }
}
