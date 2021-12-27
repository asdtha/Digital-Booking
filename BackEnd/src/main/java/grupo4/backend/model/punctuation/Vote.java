package grupo4.backend.model.punctuation;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import grupo4.backend.model.UserDB;

import javax.persistence.*;

@Entity
@Table(name = "PunctuationVotes")
public class Vote {

    //Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn( name = "punctuation_counter_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private PunctuationCounter punctuationCounter;
    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIgnoreProperties({"myBookings", "password","userType","voteList","favorites"})
    private UserDB userWhoVoted;
    private Integer numericValue;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("originalVote")
    private Comment comment;

    //Constructors
    public Vote() {
    }

    public void createAndSetComment(String text) {
        if (text != null) {
            if(text.trim().length() > 0){
                Comment created = new Comment();
                created.setOriginalVote(this);
                this.setComment(created);
                created.setText(text);
            };
        }
    }

    //Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public PunctuationCounter getPunctuationCounter() {return punctuationCounter;}
    public void setPunctuationCounter(PunctuationCounter punctuationCounter) {this.punctuationCounter = punctuationCounter;}
    public UserDB getUserWhoVoted() {
        return userWhoVoted;
    }
    public void setUserWhoVoted(UserDB userWhoVoted) {
        this.userWhoVoted = userWhoVoted;
    }
    public Comment getComment() {
        return comment;
    }
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public Integer getNumericValue() {
        return numericValue;
    }
    public void setNumericValue(Integer numericValue) {
        this.numericValue = numericValue;
    }
}
