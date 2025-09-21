package com.project.airBnb.airbnbApp.dto;

import com.project.airBnb.airbnbApp.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestDto {
    private Long id;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
}
