package com.project.airBnb.airbnbApp.controller;

import com.project.airBnb.airbnbApp.dto.*;
import com.project.airBnb.airbnbApp.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

        private final BookingService bookingService;

        @PostMapping("/init")
        @Operation(summary = "Initiate the booking", tags = {"Booking Flow"})
        public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequestDto bookingRequestDto) {
            return ResponseEntity.ok(bookingService.initializeBooking(bookingRequestDto));
        }

        @PostMapping("/{bookingId}/addGuests")
        @Operation(summary = "Add guest Ids to the booking", tags = {"Booking Flow"})
        public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                     @RequestBody List<GuestDto> guestDtos) {
            return ResponseEntity.ok(bookingService.addGuests(bookingId,guestDtos));
        }

        @PostMapping("/{bookingId}/payments")
        @Operation(summary = "Initiate payments flow for the booking", tags = {"Booking Flow"})
        public ResponseEntity<BookingPaymentInitResponseDto> initiatePayment(@PathVariable Long bookingId) {
            String sessionUrl  = bookingService.initiatePayment(bookingId);
            return ResponseEntity.ok(new BookingPaymentInitResponseDto(sessionUrl));
        }

        @PostMapping("/{bookingId}/cancel")
        @Operation(summary = "Cancel the booking", tags = {"Booking Flow"})
        public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.noContent().build();
        }

        @PostMapping("/{bookingId}/status")
        @Operation(summary = "Check the status of the booking", tags = {"Booking Flow"})
        public ResponseEntity<BookingStatusResponseDto> getBookingStatus(@PathVariable Long bookingId) {
            return ResponseEntity.ok(new BookingStatusResponseDto(bookingService.getBookingStatus(bookingId)));
        }
}
