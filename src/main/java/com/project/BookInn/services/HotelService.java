package com.project.BookInn.services;

import com.project.BookInn.dto.HotelDto;
import com.project.BookInn.dto.HotelInfoDto;

import java.util.List;

public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

//    List<HotelDto> getAllHotels();

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long id);

    HotelInfoDto getHotelInfoById(Long hotelId);


    List<HotelDto> getAllHotel();
}
