package com.chatop.api.services;

import com.chatop.api.dto.UserResponseDto;

public interface UserService {

    void createUser(String name, String email, String rawPassword);

    UserResponseDto getByEmail(String email);

    UserResponseDto getById(Integer id);

    Integer getIdByEmail(String email);

}