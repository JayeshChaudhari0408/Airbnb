package com.project.BookInn.services;

import com.project.BookInn.dto.HotelPriceResponseDto;
import com.project.BookInn.dto.HotelSearchRequest;
import com.project.BookInn.dto.InventoryDto;
import com.project.BookInn.dto.UpdateInventoryRequestDto;
import com.project.BookInn.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceResponseDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
