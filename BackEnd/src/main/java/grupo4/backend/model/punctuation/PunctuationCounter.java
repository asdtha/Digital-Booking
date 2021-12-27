package grupo4.backend.model.punctuation;

import grupo4.backend.model.Product;

import javax.persistence.*;

@Entity
@Table(name = "PuntuationCounters")
public class PunctuationCounter {

   //Attributes
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;
    private Double average;
    private int zeroStars;
    private int oneStar;
    private int twoStars;
    private int treeStars;
    private int fourStars;
    private int fiveStars;


    //Constructors
    public PunctuationCounter(Product product){
        this.product = product;
        this.average = -1.0;
    }

    //Methods
    public PunctuationCounter() {
        this.average = -1.0;
    }

    public PunctuationCounter(Long id, Product product, Double average, int zeroStars, int oneStar, int twoStars, int treeStars, int fourStars, int fiveStars) {
        this.id = id;
        this.product = product;
        this.average = average;
        this.zeroStars = zeroStars;
        this.oneStar = oneStar;
        this.twoStars = twoStars;
        this.treeStars = treeStars;
        this.fourStars = fourStars;
        this.fiveStars = fiveStars;
    }

    public void addVoteValue(Integer value){
        if(value == 0){
            this.zeroStars++;
        }else if(value == 1){
            this.oneStar++;
        }else if(value == 2){
            this.twoStars++;
        }else if(value == 3){
            this.treeStars++;
        }else if(value == 4){
            this.fourStars++;
        }else if(value == 5) {
            this.fiveStars++;
        }
        recalculateAndSetAverage();
    }

    public void discountVoteValue(Integer value){
        if(value == 0){
            this.zeroStars--;
        }else if(value == 1){
            this.oneStar--;
        }else if(value == 2){
            this.twoStars--;
        }else if(value == 3){
            this.treeStars--;
        }else if(value == 4){
            this.fourStars--;
        }else if(value == 5) {
            this.fiveStars--;
        }
        recalculateAndSetAverage();
    }

    public void recalculateAndSetAverage(){
        int sum = oneStar + 2*twoStars + 3*treeStars + 4*fourStars + 5*fiveStars;
        int count = zeroStars + oneStar + twoStars + treeStars + fourStars + fiveStars;
        if(count <= 0){
            average = -1.0;
        }else{
            average = (double)(sum*2)/(double) count;
        }
    }


    //Getters and Setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Double getAverage() {
        return average;
    }
    public void setAverage(Double average) {
        this.average = average;
    }
    public void setId(Long id) {
        this.id = id;
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
