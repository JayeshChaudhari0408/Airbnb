package com.project.BookInn.repositories;

import com.project.BookInn.entity.Guest;
import com.project.BookInn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByUser(User user);

}