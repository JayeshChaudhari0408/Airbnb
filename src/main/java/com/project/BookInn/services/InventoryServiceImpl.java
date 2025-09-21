package com.project.BookInn.services;
import com.project.BookInn.dto.*;
import com.project.BookInn.entity.Room;
import com.project.BookInn.repositories.InventoryRepository;
import com.project.BookInn.utils.AppUtils;
import com.project.BookInn.entity.Inventory;
import com.project.BookInn.entity.User;
import com.project.BookInn.exceptions.ResourceNotFoundException;
import com.project.BookInn.repositories.HotelMinPriceRepository;
import com.project.BookInn.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    private final RoomRepository roomRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final ModelMapper modelMapper;


    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        for(;!today.isAfter(endDate);today = today.plusDays(1)) {
            Inventory inventory  = Inventory.builder()
                    .room(room)
                    .hotel(room.getHotel())
                    .reservedCount(0)
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
    public void deleteAllInventories(Room room) {
        log.info("Deleting the inventories of room with id: {}", room.getId());
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceResponseDto> searchHotels(HotelSearchRequest hotelSearchRequest) {
        log.info("Searching hotels for {} city, from {} to {}", hotelSearchRequest.getCity(), hotelSearchRequest.getStartDate(), hotelSearchRequest.getEndDate());
        Pageable pageable = PageRequest.of(hotelSearchRequest.getPage(),hotelSearchRequest.getSize());

        long dateCount =
                ChronoUnit.DAYS.between(hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate())+1;

        Page<HotelPriceDto> hotelPage = hotelMinPriceRepository.findHotelsWithAvailableInventory(hotelSearchRequest.getCity(),
                hotelSearchRequest.getStartDate(),hotelSearchRequest.getEndDate(),hotelSearchRequest.getRoomsCount(),
                dateCount,pageable);

        return hotelPage.map(hotelPriceDto -> {
            HotelPriceResponseDto hotelPriceResponseDto = modelMapper.map(hotelPriceDto.getHotel(), HotelPriceResponseDto.class);
            hotelPriceResponseDto.setPrice(hotelPriceDto.getPrice());
            return hotelPriceResponseDto;
        });
    }

    @Override
    public List<InventoryDto> getAllInventoryByRoom(Long roomId) {
        log.info("Getting All inventory by room for room with id: {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() ->  new ResourceNotFoundException("Room with this id is not available"));

        User user = AppUtils.getCurrentUser();
        if(!user.equals(room.getHotel().getOwner())) {
            throw new AccessDeniedException("You are not owner of room with id: "+roomId);
        }
        return inventoryRepository.findByRoomOrderByDate(room).stream()
                .map((element) -> modelMapper.map(element, InventoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto) {
        log.info("Updating All inventory by room for room with id: {} between date range: {} - {}", roomId,
                updateInventoryRequestDto.getStartDate(), updateInventoryRequestDto.getEndDate());
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() ->  new ResourceNotFoundException("Room with this id is not available"));

        User user = AppUtils.getCurrentUser();
        if(!user.equals(room.getHotel().getOwner())) {
            throw new AccessDeniedException("You are not owner of room with id: "+roomId);
        }

        inventoryRepository.getInventoryAndLockBeforeUpdate(roomId,updateInventoryRequestDto.getStartDate(),
                updateInventoryRequestDto.getEndDate());

        inventoryRepository.updateInventory(roomId,updateInventoryRequestDto.getStartDate(),
                updateInventoryRequestDto.getEndDate(),updateInventoryRequestDto.getSurgeFactor(),
                updateInventoryRequestDto.getClosed());
    }
}
