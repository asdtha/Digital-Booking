package grupo4.backend.dto;

import grupo4.backend.model.punctuation.PunctuationCounter;

public class PuntuationCounterDTO {

    //Attributes
    private Long id;
    private Long productId;
    private Double average;
    private int zeroStars;
    private int oneStar;
    private int twoStars;
    private int treeStars;
    private int fourStars;
    private int fiveStars;


    //Constructors
    public PuntuationCounterDTO() {
    }

    public PuntuationCounterDTO(PunctuationCounter pc) {
        this.id = pc.getId();
        this.productId = pc.getProduct().getId();
        this.average = pc.getAverage();
        this.zeroStars = pc.getZeroStars();
        this.oneStar = pc.getOneStar();
        this.twoStars = pc.getTwoStars();
        this.treeStars = pc.getTreeStars();
        this.fourStars = pc.getFourStars();
        this.fiveStars = pc.getFiveStars();
    }


    //Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Double getAverage() {
        return average;
    }
    public void setAverage(Double average) {
        this.average = average;
    }
    public int getZeroStars() {
        return zeroStars;
    }
    public void setZeroStars(int zeroStars) {
        this.zeroStars = zeroStars;
    }
    public int getOneStar() {
        return oneStar;
    }
    public void setOneStar(int oneStar) {
        this.oneStar = oneStar;
    }
    public int getTwoStars() {
        return twoStars;
    }
    public void setTwoStars(int twoStars) {
        this.twoStars = twoStars;
    }
    public int getTreeStars() {
        return treeStars;
    }
    public void setTreeStars(int treeStars) {
        this.treeStars = treeStars;
    }
    public int getFourStars() {
        return fourStars;
    }
    public void setFourStars(int fourStars) {
        this.fourStars = fourStars;
    }
    public int getFiveStars() {
        return fiveStars;
    }
    public void setFiveStars(int fiveStars) {
        this.fiveStars = fiveStars;
    }
}
