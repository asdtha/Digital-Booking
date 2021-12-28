package grupo4.backend.repository;

import grupo4.backend.model.punctuation.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
