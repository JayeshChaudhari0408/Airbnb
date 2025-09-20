package com.project.airBnb.airbnbApp.services;

import com.project.airBnb.airbnbApp.dto.BookingDto;
import com.project.airBnb.airbnbApp.dto.BookingRequestDto;
import com.project.airBnb.airbnbApp.dto.GuestDto;
import com.project.airBnb.airbnbApp.dto.HotelReportDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingDto initializeBooking(BookingRequestDto bookingRequestDto);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtos);


    String initiatePayment(Long bookingId);

    void capturePayment(Event event) throws StripeException;

    void cancelBooking(Long bookingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<BookingDto> getMyBookings();
}
