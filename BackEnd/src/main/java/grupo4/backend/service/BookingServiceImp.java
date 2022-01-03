package grupo4.backend.service;
import grupo4.backend.exception.BadRequestException;
import grupo4.backend.model.Booking;
import grupo4.backend.model.Product;
import grupo4.backend.model.UserDB;
import grupo4.backend.repository.BookingRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BookingServiceImp implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    final static Logger logger = Logger.getLogger(BookingServiceImp.class);

    @Override
    public Booking searchBookingById(Long id) throws BadRequestException {
        if (id == null) {
            logger.error("Se produjo un error: el id del booking es null");
            throw new BadRequestException("id del booking es null");
        }
        Optional<Booking> optBooking = bookingRepository.findById(id);
        if(optBooking.isPresent()){
            return optBooking.get();
        }else{
            throw new BadRequestException("No existe Booking con id: "+ id);
        }
    }

    @Override
    public Booking saveBooking(Booking booking) throws BadRequestException{
        boolean hasAsignedId = booking.getId() != null;
        boolean hasValidStartingHour = booking.getStartingHour() <= 24;

        //check in es anterior a check out && checkIn no es anterior a hoy

        boolean hasValidDateInterval = booking.getCheckInDate().isBefore(booking.getCheckOutDate());

        hasValidDateInterval &= !(booking.getCheckInDate().isBefore(LocalDate.now()));
        if(hasAsignedId){
            logger.error("Se produjo un error al guardar una reserva con id aignado");
            throw new BadRequestException("No es posible guardar una reserva con id asignado.");}

        if(!hasValidDateInterval){throw new BadRequestException("El intervalo de fechas no es válido.");}
        if(!hasValidStartingHour){throw new BadRequestException("La hora inicial debe ser menor o igual a 24.");}
        Product product = productService.searchProductByIdAsClass(booking.getProduct().getId());
        UserDB user = userService.searchUserByIdAsClass(booking.getUser().getId());
        boolean checkInHourIsInRange = product.getEarliestCheckInHour() <= booking.getStartingHour();
        checkInHourIsInRange &= product.getLatestCheckInHour()>= booking.getStartingHour();
        if(!checkInHourIsInRange){throw new BadRequestException("La hora de checkIn no es aceptada por este local.");}
        boolean dateIntervalIsAvailable = true;
        for (Booking oldBooking: product.getBookingList()
             ) {        //Warning: "!isBefore" === "isBeforeOrEquals". El equals es importante para casos raros. //TODO esto tiene que ser entendible, quizas latex?
            boolean doesntOverlap = !booking.getCheckInDate().isBefore(oldBooking.getCheckOutDate());
            doesntOverlap |= !booking.getCheckOutDate().isAfter(oldBooking.getCheckInDate());
            dateIntervalIsAvailable &= doesntOverlap;
        }
        if(!dateIntervalIsAvailable){
            throw new BadRequestException("El intervalo seleccionado no está libre");
        }else{
            Booking saved = bookingRepository.save(booking);
            product.addBooking(saved);
            user.addBooking(saved);
            return saved;
        }
    }

    @Override
    public Boolean deleteBookingById(Long id) throws BadRequestException{
        Optional<Booking> optBooking = bookingRepository.findById(id);
        if(optBooking.isPresent()){
            bookingRepository.deleteById(id);
            return true;
        }else{
            throw new BadRequestException("no existe una reserva con el id "+ id);
        }
    }

    @Override
    public List<Booking> getBookingsByProduct(Long productId) throws BadRequestException {
        if(productId == null){
            throw new BadRequestException("el id es nulo");
        }
        try{
            productService.searchProductById(productId);
        }catch (Exception e){
            throw new BadRequestException("El Id de Producto no corresponde con un producto en nuestra base de datos");
        }
        List<Booking> results = bookingRepository.getBookingByProductId(productId);
        setUsersAsNull(results);
        return results;
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) throws BadRequestException {
        if(userId == null){throw new BadRequestException("el id es nulo");}
        try{
            userService.searchUserById(userId);
        }catch (Exception e){
            throw new BadRequestException("El Id de Usuario no corresponde con un usuario en nuestra base de datos");
        };
        List<Booking> results = bookingRepository.getBookingByUserId(userId);

        return results;
    }


    private void setUsersAsNull(List<Booking> bookingList){
        for (Booking b: bookingList
             ) {
            b.setUser(null);
        }
    }
}
