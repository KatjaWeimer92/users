package de.ait.users.controller;


import de.ait.users.dto.UserRequestDto;
import de.ait.users.dto.UserResponseDto;
import de.ait.users.entity.User;
import de.ait.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    // GET   /users
    // GET   /users?n=jack

    @GetMapping("/users")
    public List<UserResponseDto> getUsers(@RequestParam(name = "n", required = false, defaultValue = "") String name,
                                          @RequestParam(name = "e", required = false, defaultValue = "") String email) {

        return service.getUsers(name, email);

    }


    //localhost:8080/
    @PostMapping("/users")
    public UserResponseDto createNewUser(@RequestBody UserRequestDto userDto) {
        return service.createNewUser(userDto);
    }

    @GetMapping("/users/{id}")
    public UserResponseDto getUserById(@PathVariable(name = "id") Long id) {
        return service.findById(id);
    }


    @PutMapping("/users/{id}")
    public UserResponseDto updateUser(@PathVariable(name = "id") Long id, @RequestBody UserRequestDto userRequestDto) {
        return service.updateUser(id, userRequestDto);
    }
}