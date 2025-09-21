package com.project.BookInn.services;

import com.project.BookInn.dto.BookingRequestDto;
import com.project.BookInn.dto.HotelReportDto;
import com.project.BookInn.dto.BookingDto;
import com.project.BookInn.dto.GuestDto;
import com.project.BookInn.entity.enums.BookingStatus;
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

    BookingStatus getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<BookingDto> getMyBookings();
}
