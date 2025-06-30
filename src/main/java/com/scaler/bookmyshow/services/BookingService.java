package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.exceptions.SeatBookedException;
import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.BookingRepository;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    private PricingService pricingService;

    @Autowired
    public BookingService(
            UserRepository userRepository,
            ShowRepository showRepository,
            ShowSeatRepository showSeatRepository,
            BookingRepository bookingRepository,
            PricingService pricingService
    ) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.pricingService = pricingService;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, List<Long> showSeatIds, Long showId) throws SeatBookedException {
        /*
        * --- Take the lock here for today's class
        * Get the user using the userId from the DB
        * Get the show using the showId from the DB
        * ----- Take lock here
        * Get the show seat using the show seat ids from the DB
        * Check the availability of seats
        * If available (or locked at more than 15 minutes), change the status
        * ---- End the lock
        * Save to DB the updated show seats and the booking
        * ---- End the lock here for today's class
        * */

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            // TODO: Add UserNotFoundException here
            throw new RuntimeException();
        }

        User bookedBy = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()) {
            throw new RuntimeException();
        }

        Show bookedShow = showOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        for(ShowSeat showSeat: showSeats) {
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new SeatBookedException();
            }
        }

        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for(ShowSeat showSeat: showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }


        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setShow(bookedShow);
        booking.setShowSeats(savedShowSeats);
        booking.setBookedAt(new Date());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setAmount(
                pricingService.calculatePrice(savedShowSeats, bookedShow)
        );

        return bookingRepository.save(booking);
    }
}
