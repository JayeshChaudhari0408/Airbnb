package com.project.BookInn.dto;

import lombok.Data;

@Data
public class SignUpRequestDto {
    private String email;
    private String name;
    private String password;
}
