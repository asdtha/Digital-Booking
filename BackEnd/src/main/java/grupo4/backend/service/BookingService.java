package grupo4.backend.service;

import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Booking;
import grupo4.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookingService {
    public Booking searchBookingById(Long id) throws BadRequestException;
    public Booking saveBooking(Booking booking) throws BadRequestException;
    public Boolean deleteBookingById(Long id) throws BadRequestException;
    public List<Booking> getBookingsByProduct(Long id) throws BadRequestException;
    public List<Booking> getBookingsByUser(Long id) throws BadRequestException;


}
