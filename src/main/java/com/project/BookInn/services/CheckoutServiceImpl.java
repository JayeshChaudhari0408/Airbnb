package com.project.BookInn.services;

import com.project.BookInn.entity.Booking;
import com.project.BookInn.entity.User;
import com.project.BookInn.repositories.BookingRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {
    private final BookingRepository bookingRepository;

    @Override
    public String getCheckoutSession(Booking booking, String successUrl, String failureUrl) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setName(user.getName())
                    .setEmail(user.getEmail())
                    .build();
            Customer customer = Customer.create(customerCreateParams);
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(booking.getHotel().getName() +" : "+booking.getRoom().getType())
                    .setDescription("Booking id: "+booking.getId())
                    .build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("inr")
                    .setUnitAmount(booking.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                    .setProductData(productData)
                    .build();


            SessionCreateParams.LineItem lineItems = SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)
                    .setPriceData(priceData)
                    .build();

            SessionCreateParams sessionParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                    .setCustomer(customer.getId())
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(failureUrl)
                    .addLineItem(lineItems)
                    .build();

            Session session = Session.create(sessionParams);

            booking.setPaymentSessionId(session.getId());
            bookingRepository.save(booking);
            return session.getUrl();

        }catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
