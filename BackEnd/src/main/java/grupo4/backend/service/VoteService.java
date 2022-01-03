package grupo4.backend.service;

import grupo4.backend.dto.VoteDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.UserDB;
import grupo4.backend.model.punctuation.Vote;

import java.util.List;


public interface VoteService {
    Vote saveVote(VoteDTO voteDTO, UserDB user) throws BadRequestException;
    VoteDTO searchVoteById(Long id) throws BadRequestException;
    VoteDTO updateVote(VoteDTO product) throws BadRequestException;
    boolean deleteVoteById(Long id) throws BadRequestException;
    List<VoteDTO> getVotesFromProductWithId(Long productId);
}
