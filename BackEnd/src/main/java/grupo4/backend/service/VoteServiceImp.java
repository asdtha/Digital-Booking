package grupo4.backend.service;

import grupo4.backend.dto.VoteDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.UserDB;
import grupo4.backend.model.punctuation.Comment;
import grupo4.backend.model.punctuation.PunctuationCounter;
import grupo4.backend.model.punctuation.Vote;
import grupo4.backend.repository.CommentRepository;
import grupo4.backend.repository.PunctuationCounterRepository;
import grupo4.backend.repository.VoteRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoteServiceImp implements VoteService{

    private VoteRepository voteRepository;
    private PunctuationCounterRepository punctuationRepo;
    private CommentRepository commentRepository;
    final static Logger logger = Logger.getLogger(VoteServiceImp.class);

    @Autowired
    public VoteServiceImp(VoteRepository voteRepository, PunctuationCounterRepository punctuationRepo, CommentRepository commentRepository) {
        this.voteRepository = voteRepository;
        this.punctuationRepo = punctuationRepo;
        this.commentRepository = commentRepository;
    }

    @Override
    public Vote saveVote(VoteDTO voteDTO, UserDB user) throws BadRequestException{
        boolean hasMandatoryValues = voteDTO.getNumericValue() != null && voteDTO.getPunctuationId() != null;
        if(!hasMandatoryValues){throw new BadRequestException("El voto no tiene valores obligatorios: valor numérico y/o id de contador de puntuación");}
        boolean idIsAlreadyAsigned = voteDTO.getId() != null;
        if(idIsAlreadyAsigned){throw new BadRequestException("El voto ya tiene un id asignado, no es posible guardarlo.");}
        PunctuationCounter punctuationInDataBase = getPunctuation(voteDTO.getPunctuationId());
        boolean invalidValue = voteDTO.getNumericValue() > 5 || voteDTO.getPunctuationId() < 0;
        if(invalidValue){throw new BadRequestException("El valor del voto es incorrecto. (entre 0 y 5)");}
        boolean alreadyVoted = userAlreadyVotedOn(user, voteDTO.getPunctuationId());
        if(alreadyVoted){
            logger.error("El usuario " + user + "ya realizó un voto en este producto.");
            throw new BadRequestException("Usuario ya votó en este producto");}

        PunctuationCounter punctuationCounter = getPunctuationCounterById(voteDTO.getPunctuationId());
        Vote toSave = new Vote();
        toSave.setNumericValue(voteDTO.getNumericValue());
        toSave.setPunctuationCounter(punctuationCounter);
        toSave.setUserWhoVoted(user);
        toSave.createAndSetComment(voteDTO.getText());
        punctuationCounter.addVoteValue(toSave.getNumericValue());
        return voteRepository.save(toSave);
    }

    @Override
    public VoteDTO searchVoteById(Long id) throws BadRequestException {
        return new VoteDTO(searchVoteByIdAsClass(id));
    }

    public Vote searchVoteByIdAsClass(Long id) throws BadRequestException{
        Optional<Vote> optVote = voteRepository.findById(id);
        if(optVote.isPresent()){
            return optVote.get();
        }else{
            throw new BadRequestException("El id del voto no existe");
        }
    }

    @Override
    public VoteDTO updateVote(VoteDTO voteDTO) throws BadRequestException {
        Vote voteInDataBase = searchVoteByIdAsClass(voteDTO.getId());
        boolean commentChanged = false;
        String oldText = null;
        if(voteInDataBase.getComment() != null){
            oldText = voteInDataBase.getComment().getText();
        }
        if(oldText == null){
            commentChanged = voteDTO.getText() == null;
        }else{
            commentChanged = oldText.equals(voteDTO.getText());
        }
        if(commentChanged){
            if(voteDTO.getText() == null){
                voteInDataBase.setComment(null);
                commentRepository.deleteById(voteInDataBase.getId());
            }else{
                if(voteInDataBase.getComment() == null){
                    Comment newComment = new Comment();
                    newComment.setText(voteDTO.getText());
                    newComment.setOriginalVote(voteInDataBase);
                    voteInDataBase.setComment(newComment);
                }else{
                    Comment oldComment = voteInDataBase.getComment();
                    oldComment.setText(voteDTO.getText());
                    voteInDataBase.setComment(oldComment);
                }
            }
        }
        if(voteDTO.getNumericValue() == null){
            throw new BadRequestException("el nuevo valor es nulo");}
        if(voteDTO.getNumericValue()> 5 || voteDTO.getNumericValue() < 0){
            throw new BadRequestException("El valor numérico es mayor a 5 o menor a 0");
        }
        boolean valueChange = ! voteInDataBase.getNumericValue().equals(voteDTO.getNumericValue());
        if(valueChange){
            PunctuationCounter pc = getPunctuationCounterById(voteDTO.getPunctuationId());
            pc.discountVoteValue(voteInDataBase.getNumericValue());
            pc.discountVoteValue(voteDTO.getNumericValue());
            voteInDataBase.setNumericValue(voteDTO.getNumericValue());
            punctuationRepo.save(pc);
        }
        if(valueChange || commentChanged){
            voteRepository.save(voteInDataBase);
        }
        return voteDTO;
    }

    @Override
    public boolean deleteVoteById(Long id) throws BadRequestException {
        Optional<Vote> optVote = voteRepository.findById(id);
        if(optVote.isEmpty()){throw new BadRequestException("No tenemos ningún voto con ese id");}
        Vote voteToDelete = optVote.get();
        PunctuationCounter pc = voteToDelete.getPunctuationCounter();
        pc.discountVoteValue(voteToDelete.getNumericValue());
        voteRepository.deleteById(id);
        return true;
    }

    @Override
    public List<VoteDTO> getVotesFromProductWithId(Long productId){
        List<Vote> result = voteRepository.getAllVotesWithProductId(productId);
        return result.stream().map(VoteDTO::new).collect(Collectors.toList());
    }



    PunctuationCounter getPunctuation(Long punctuationId) throws BadRequestException{
        if(punctuationId == null){throw new BadRequestException("El id de punctuation es nulo.");}
        Optional<PunctuationCounter> optPunctuation = punctuationRepo.findById(punctuationId);
        if(optPunctuation.isPresent()){
            return optPunctuation.get();
        }else{
            throw new BadRequestException("El id del contador de puntuación es inválido o no existe en nuestra base de datos.");
        }
    }

    //Private methods to shorten code
    private boolean userAlreadyVotedOn(UserDB user, Long punctuationCounterId){
        for (Vote vote : user.getVoteList()) {
            if (vote.getPunctuationCounter().getId() == punctuationCounterId){
                return true;
            }
        }
        return false;
    }

    private PunctuationCounter getPunctuationCounterById(Long pcId) throws BadRequestException{
        Optional<PunctuationCounter> optPC = punctuationRepo.findById(pcId);
        if(optPC.isEmpty()){
            throw new BadRequestException("No existe ese PunctuationCounter en nuestra base de datos.");
        }
        return optPC.get();
    }
}
