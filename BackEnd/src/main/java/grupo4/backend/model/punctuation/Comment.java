package grupo4.backend.model.punctuation;
import javax.persistence.*;

@Entity
@Table(name="Comments")
public class Comment {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1200)
    private String text;
    @OneToOne
    private Vote originalVote;


    //Constructors
    public Comment() {
    }

    //Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Vote getOriginalVote() {
        return originalVote;
    }
    public void setOriginalVote(Vote originalVote) {
        this.originalVote = originalVote;
    }
}
