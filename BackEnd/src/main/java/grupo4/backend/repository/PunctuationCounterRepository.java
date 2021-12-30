package grupo4.backend.repository;

import grupo4.backend.model.punctuation.PunctuationCounter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PunctuationCounterRepository extends JpaRepository<PunctuationCounter, Long> {
}
