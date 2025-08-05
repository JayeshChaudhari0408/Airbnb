package com.project.airBnb.airbnbApp.services;

import com.project.airBnb.airbnbApp.entity.Room;

import java.time.LocalDate;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventory( Room room);
}
