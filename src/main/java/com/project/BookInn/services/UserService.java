package com.project.BookInn.services;

import com.project.BookInn.dto.ProfileUpdateRequestDto;
import com.project.BookInn.dto.UserDto;
import com.project.BookInn.entity.User;

public interface UserService {

    User getUserById(Long userId);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
