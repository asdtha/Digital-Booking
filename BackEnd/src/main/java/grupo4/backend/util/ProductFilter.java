package grupo4.backend.util;

import java.time.LocalDate;

public class ProductFilter {

    //Attributes
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long cityId;
    private Long categoryId;
    private Integer offset;
    private Integer limit;

    //Constructors
    public ProductFilter() {
    }

    public ProductFilter(LocalDate checkInDate, LocalDate checkOutDate, Long cityId, Long categoryId) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.cityId = cityId;
        this.categoryId = categoryId;
    }

    //Getters and setters
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public Integer getOffset() {
        return offset;
    }
    public void setOffset(Integer offset) {
        this.offset = offset;
    }
    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
