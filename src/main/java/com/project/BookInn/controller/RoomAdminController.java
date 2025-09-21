package com.project.BookInn.controller;


import com.project.BookInn.services.RoomService;
import com.project.BookInn.dto.RoomDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId}/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomAdminController {

    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "Create a new room in a hotel", tags = {"Admin Inventory"})
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId ,@RequestBody RoomDto roomDto) {
        RoomDto room = roomService.createNewRoom(hotelId,roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all rooms in a hotel", tags = {"Admin Inventory"})
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId) {
        List<RoomDto> roomDto = roomService.getAllRoomsInHotel(hotelId);
        return ResponseEntity.ok(roomDto);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "Get a room by id", tags = {"Admin Inventory"})
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId}")
    @Operation(summary = "Delete a room by id", tags = {"Admin Inventory"})
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long roomId) {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomId}")
    @Operation(summary = "Update a room", tags = {"Admin Inventory"})
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable Long roomId, @PathVariable Long hotelId,
                                                  @RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.updateRoomById(hotelId,roomId,roomDto));
    }


}
