package com.project.airBnb.airbnbApp.controller;

import com.project.airBnb.airbnbApp.services.BookingService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class WebhookController {

    private final BookingService bookingService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/payment")
    public ResponseEntity<Void> capturePayment(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload,sigHeader,endpointSecret);
            bookingService.capturePayment(event);
            return ResponseEntity.noContent().build();
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}


