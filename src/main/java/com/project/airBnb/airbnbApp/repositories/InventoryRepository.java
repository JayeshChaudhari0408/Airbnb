package com.project.airBnb.airbnbApp.repositories;

import com.project.airBnb.airbnbApp.entity.Inventory;
import com.project.airBnb.airbnbApp.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
