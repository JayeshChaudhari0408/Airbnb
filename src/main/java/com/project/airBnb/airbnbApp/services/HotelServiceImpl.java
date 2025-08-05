package com.project.airBnb.airbnbApp.services;

import com.project.airBnb.airbnbApp.dto.HotelDto;
import com.project.airBnb.airbnbApp.entity.Hotel;
import com.project.airBnb.airbnbApp.entity.Room;
import com.project.airBnb.airbnbApp.exceptions.ResourceNotFoundException;
import com.project.airBnb.airbnbApp.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    private final InventoryService inventoryService;
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel= modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);
        hotel=hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}", hotelDto.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting a hotel by ID", id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id "+id));
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto  hotelDto) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+id));
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+id));
        hotelRepository.deleteById(id);
        //TODO: delete the future inventories for this hotel
        for(Room room: hotel.getRooms()) {
            inventoryService.deleteFutureInventory(room);
        }


    }

    @Override
    public void activateHotel(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+id));

        hotel.setActive(true);

        // assuming doing only once
        for(Room room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }

    }


}
