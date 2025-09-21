package com.project.BookInn.strategy;

import com.project.BookInn.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
