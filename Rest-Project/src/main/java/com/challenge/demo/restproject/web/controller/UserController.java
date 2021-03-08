package com.challenge.demo.restproject.web.controller;

import com.challenge.demo.restproject.converters.RegistrationConverter;
import com.challenge.demo.restproject.dto.UserDto;
import com.challenge.demo.restproject.entity.User;
import com.challenge.demo.restproject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final RegistrationConverter registrationConverter;

    @Autowired
    public UserController(UserService userService, RegistrationConverter registrationConverter) {
        this.userService = userService;
        this.registrationConverter = registrationConverter;
    }

    @GetMapping("")
    public List<User> users() {
        log.info("process=get-users");
        return userService.allUsers();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> user(@PathVariable Long id) {
        log.info("process=get-user, user_id={}", id);
        Optional<User> user = userService.userById(id);
        return user.map(u -> ResponseEntity.ok(u))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public boolean createUser(@RequestBody UserDto user) {
        log.info("process=create-user, user_name={}", user.getName());
        try {
            userService.createUser(registrationConverter.convertToUser(user));
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        log.info("process=update-user, user_id={}", id);
        user.setUserId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        log.info("process=delete-user, user_id={}", id);
        userService.deleteUser(id);
    }


}
