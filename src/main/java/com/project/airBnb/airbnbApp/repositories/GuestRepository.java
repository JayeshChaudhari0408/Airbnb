package com.project.airBnb.airbnbApp.repositories;

import com.project.airBnb.airbnbApp.entity.Guest;
import com.project.airBnb.airbnbApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByUser(User user);

}