package grupo4.backend.dto;
import grupo4.backend.model.punctuation.Vote;


public class VoteDTO {

    //Attributes
    private Long id;
    private Long punctuationId;
    private Long userId;
    private String userFirstName;
    private Integer numericValue;
    private String text;


    //Constructors
    public VoteDTO() {
    }

    public VoteDTO(Vote vote){
        this.id = vote.getId();
        this.punctuationId = vote.getPunctuationCounter().getId();
        this.userId = vote.getUserWhoVoted().getId();
        this.userFirstName = vote.getUserWhoVoted().getFirstName();
        this.numericValue = vote.getNumericValue();
        if(vote.getComment() != null){
            this.text = vote.getComment().getText();
        }
    }


    //Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPunctuationId() {
        return punctuationId;
    }
    public void setPunctuationId(Long puntuationId) {
        this.punctuationId = punctuationId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserFirstName() {
        return userFirstName;
    }
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }
    public Integer getNumericValue() {
        return numericValue;
    }
    public void setNumericValue(Integer numericPunctuation) {
        this.numericValue = numericPunctuation;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
