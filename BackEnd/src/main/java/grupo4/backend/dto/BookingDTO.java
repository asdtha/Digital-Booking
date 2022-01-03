package grupo4.backend.dto;
import grupo4.backend.model.Booking;
import java.time.LocalDate;

public class BookingDTO {

    //Attributes
private LocalDate checkIn;
private LocalDate checkOut;

    //Constructors
    public BookingDTO() {
    }

    public BookingDTO(Booking booking) {
        LocalDate today = LocalDate.now();
        boolean reservationAlreadyEnded = today.isBefore(booking.getCheckOutDate());
        if(reservationAlreadyEnded){
            this.checkIn = booking.getCheckInDate();
            this.checkOut = booking.getCheckOutDate();
        }
    }

    //Getters and Setters
    public LocalDate getCheckIn() {
        return checkIn;
    }
    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }
    public LocalDate getCheckOut() {
        return checkOut;
    }
    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }
}
