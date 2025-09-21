package com.project.BookInn.dto;

import com.project.BookInn.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {

    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
}
