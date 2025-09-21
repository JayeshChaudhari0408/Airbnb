package com.project.BookInn.services;

import com.project.BookInn.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
