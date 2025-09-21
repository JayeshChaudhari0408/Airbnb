package com.project.BookInn.services;

import com.project.BookInn.dto.HotelDto;
import com.project.BookInn.dto.HotelInfoDto;
import com.project.BookInn.dto.RoomDto;
import com.project.BookInn.entity.Hotel;
import com.project.BookInn.entity.Room;
import com.project.BookInn.exceptions.ResourceNotFoundException;
import com.project.BookInn.exceptions.UnauthorisedException;
import com.project.BookInn.repositories.InventoryRepository;
import com.project.BookInn.utils.AppUtils;
import com.project.BookInn.entity.User;
import com.project.BookInn.repositories.HotelRepository;
import com.project.BookInn.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name: {}", hotelDto.getName());
        Hotel hotel= modelMapper.map(hotelDto,Hotel.class);
        hotel.setActive(false);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        hotel.setOwner(user);

        hotel=hotelRepository.save(hotel);
        log.info("Created a new hotel with ID: {}", hotelDto.getId());
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting a hotel by ID"+ id);
        Hotel hotel=hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found with id "+id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorisedException("This user does not own this hotel with id: "+id);
        }
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto  hotelDto) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+id));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorisedException("This user does not own this hotel with id: "+id);
        }
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorisedException("This user does not own this hotel with id: "+id);
        }

        for(Room room: hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public void activateHotel(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+id));

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.equals(hotel.getOwner())) {
            throw new UnauthorisedException("This user does not own this hotel with id: "+id);
        }
        hotel.setActive(true);

        // assuming doing only once
        for(Room room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }

    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id "+hotelId));


        List<RoomDto> rooms =hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element,RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel,HotelDto.class),rooms);
    }

    @Override
    public List<HotelDto> getAllHotel() {
        User user = AppUtils.getCurrentUser();
        List<Hotel> hotels = hotelRepository.findByOwner(user);

        return hotels
                .stream()
                .map((element) -> modelMapper.map(element,HotelDto.class))
                .collect(Collectors.toList());
    }


}
