package com.project.BookInn.repositories;

import com.project.BookInn.entity.Hotel;
import com.project.BookInn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findByOwner(User user);
}
