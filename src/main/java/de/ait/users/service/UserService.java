package de.ait.users.service;

import de.ait.users.dto.UserRequestDto;
import de.ait.users.dto.UserResponseDto;
import de.ait.users.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getUsers(String name, String email);

    //List<User> findAll();
    UserResponseDto createNewUser(UserRequestDto user);
    UserResponseDto findById(Long id);
    //List<User> findByName(String name);

    UserResponseDto updateUser(Long id, UserRequestDto user);

}