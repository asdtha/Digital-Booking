package grupo4.backend.controller;

import grupo4.backend.dto.BookingDTO;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Booking;
import grupo4.backend.model.UserDB;
import grupo4.backend.model.Product;
import grupo4.backend.model.UserDB;
import grupo4.backend.service.*;
import grupo4.backend.util.JwtUtil;
import org.apache.coyote.Response;
import grupo4.backend.service.UserServiceImp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/API/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    //Dependency injections

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    private final static Logger logger = Logger.getLogger(BookingController.class);

    //CRUD methods
    @GetMapping("/{id}")
    public ResponseEntity<Booking> searchBookingById(@PathVariable Long id) throws BadRequestException{
        Booking foundBooking = bookingService.searchBookingById(id);
        return ResponseEntity.ok(foundBooking);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
        public ResponseEntity<Booking> saveBooking(@RequestBody Booking booking, HttpServletRequest request) throws BadRequestException{
        String bearerJwt = request.getHeader("Authorization");
        UserDB foundUser = userService.findByBearerJwt(bearerJwt);
        booking.setUser(foundUser);
        Booking savedBooking = bookingService.saveBooking(booking);
        logger.debug("El usuario " + foundUser + " con el rol " + foundUser.getUserType() + " está solicitando una reserva");
        return new ResponseEntity<Booking>( savedBooking, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBooking(@PathVariable Long id) throws BadRequestException{
        if(bookingService.deleteBookingById(id)) {
            return ResponseEntity.ok("Se eliminó la reserva con id: "+ id);
        } else {
            return new ResponseEntity("No existe una reserva con este id.", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/byProduct/{product_id}")
    public ResponseEntity<List<Booking>> bookingsByProduct(@PathVariable Long product_id) throws BadRequestException{
        return ResponseEntity.ok(bookingService.getBookingsByProduct(product_id));
    }

    @GetMapping("/byUser/{user_id}")
    public ResponseEntity<List<Booking>> bookingsByUser(@PathVariable Long user_id) throws BadRequestException{
        return ResponseEntity.ok(bookingService.getBookingsByUser(user_id));
    }



}
