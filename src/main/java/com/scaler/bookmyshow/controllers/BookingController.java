package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.BookingRequestDto;
import com.scaler.bookmyshow.dtos.BookingResponseDto;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookingResponseDto bookMovie(BookingRequestDto requestDto) {
        BookingResponseDto responseDto = new BookingResponseDto();

        Booking booking;

        try {
            booking = bookingService.bookMovie(
                    requestDto.getUserId(),
                    requestDto.getShowSeatIds(),
                    requestDto.getShowId()
            );

            responseDto.setAmount(booking.getAmount());
            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
