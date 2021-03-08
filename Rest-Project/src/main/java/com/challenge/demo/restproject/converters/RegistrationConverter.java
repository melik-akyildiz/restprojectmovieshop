package com.challenge.demo.restproject.converters;

import com.challenge.demo.restproject.dto.UserDto;
import com.challenge.demo.restproject.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class RegistrationConverter {

    public User convertToUser(UserDto userDto) throws RuntimeException {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setUsername(userDto.getName());
        user.setPassword(encodedPassword);

        return user;
    }
}
