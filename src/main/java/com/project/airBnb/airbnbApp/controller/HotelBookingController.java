package com.project.airBnb.airbnbApp.controller;

import com.project.airBnb.airbnbApp.dto.BookingDto;
import com.project.airBnb.airbnbApp.dto.BookingRequestDto;
import com.project.airBnb.airbnbApp.dto.GuestDto;
import com.project.airBnb.airbnbApp.services.BookingService;
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
        public ResponseEntity<BookingDto> initializeBooking(@RequestBody BookingRequestDto bookingRequestDto) {
            return ResponseEntity.ok(bookingService.initializeBooking(bookingRequestDto));
        }

        @PostMapping("/{bookingId}/addGuests")
        public  ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId,
                                                     @RequestBody List<GuestDto> guestDtos) {
            return ResponseEntity.ok(bookingService.addGuests(bookingId,guestDtos));
        }
}
