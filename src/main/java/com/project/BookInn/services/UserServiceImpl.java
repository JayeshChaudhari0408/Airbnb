package com.project.BookInn.services;

import com.project.BookInn.dto.ProfileUpdateRequestDto;
import com.project.BookInn.utils.AppUtils;
import com.project.BookInn.dto.UserDto;
import com.project.BookInn.entity.User;
import com.project.BookInn.exceptions.ResourceNotFoundException;
import com.project.BookInn.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+userId));

    }

    @Override
    public void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto) {
        User user = AppUtils.getCurrentUser();

        if(profileUpdateRequestDto.getDateOfBirth() != null) user.setDateOfBirth(profileUpdateRequestDto.getDateOfBirth());
        if(profileUpdateRequestDto.getGender() != null) user.setGender(profileUpdateRequestDto.getGender());
        if(profileUpdateRequestDto.getName() != null) user.setName(profileUpdateRequestDto.getName());

        userRepository.save(user);
    }

    @Override
    public UserDto getMyProfile() {
        log.info("Getting the profile for user with id: {}", AppUtils.getCurrentUser().getId());
        return modelMapper.map(AppUtils.getCurrentUser(),UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }

}
