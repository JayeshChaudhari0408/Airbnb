package com.project.airBnb.airbnbApp.services;

import com.project.airBnb.airbnbApp.entity.Inventory;
import com.project.airBnb.airbnbApp.entity.Room;
import com.project.airBnb.airbnbApp.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;


    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        for(;!today.isAfter(endDate);today = today.plusDays(1)) {
            Inventory inventory  = Inventory.builder()
                    .room(room)
                    .hotel(room.getHotel())
                    .city(room.getHotel().getCity())
                    .price(room.getBasePrice())
                    .date(today)
                    .bookedCount(0)
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteFutureInventory(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByDateAfterAndRoom(today,room);
    }
}
