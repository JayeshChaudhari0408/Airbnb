package com.project.airBnb.airbnbApp.services;

import com.project.airBnb.airbnbApp.dto.RoomDto;
import com.project.airBnb.airbnbApp.entity.Hotel;
import com.project.airBnb.airbnbApp.entity.Room;
import com.project.airBnb.airbnbApp.exceptions.ResourceNotFoundException;
import com.project.airBnb.airbnbApp.repositories.HotelRepository;
import com.project.airBnb.airbnbApp.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    private final InventoryService inventoryService;

    @Override
    @Transactional
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
        Room room = modelMapper.map(roomDto,Room.class);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+hotelId));
        room.setHotel(hotel);
        room = roomRepository.save(room);

        if(hotel.getActive()) {
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+hotelId));

        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element,RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() ->  new ResourceNotFoundException("Room with this id is not available"));
        return modelMapper.map(room,RoomDto.class);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() ->  new ResourceNotFoundException("Room with this id is not available"));

        inventoryService.deleteAllInventories(room);

        roomRepository.deleteById(roomId);
    }
}
