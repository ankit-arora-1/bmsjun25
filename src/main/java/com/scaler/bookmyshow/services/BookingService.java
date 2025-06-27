package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Booking;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    public Booking bookMovie(Long userId, List<Long> showSeatIds, Long showId) {
        /*
        * Get the user using the userId from the DB
        * Get the show using the showId from the DB
        * Get the show seat using the show seat ids from the DB
        * Check the availability of seats
        * If available (or locked at more than 15 minutes), change the status
        * Save to DB the updated show seats and the booking
        * */


        return null;
    }
}
