package grupo4.backend.repository;

import grupo4.backend.model.punctuation.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository <grupo4.backend.model.punctuation.Vote, Long> {
    @Query(value= "select V.* from punctuation_votes V where V.punctuation_counter_id = ("+
            "select punctuation_counters.id from punctuation_counters "+
            "where punctuation_counters.product_id = :productId)"
    ,nativeQuery = true)
List<Vote> getAllVotesWithProductId(Long productId);
}
