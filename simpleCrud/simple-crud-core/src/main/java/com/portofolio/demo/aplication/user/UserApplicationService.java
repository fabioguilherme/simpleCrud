package com.portofolio.demo.aplication.user;

import com.portofolio.demo.aplication.user.model.CreateUserRequest;
import com.portofolio.demo.aplication.user.model.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserApplicationService {

    UserDto save(CreateUserRequest request);

    void deleteById(Long id);

    Optional<UserDto> getById(Long id);

    List<UserDto> getAll();
}
