package com.project.airBnb.airbnbApp.services;

import com.project.airBnb.airbnbApp.dto.HotelDto;
import com.project.airBnb.airbnbApp.dto.HotelSearchRequest;
import com.project.airBnb.airbnbApp.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
