package com.portofolio.demo.infrastructure.persistence.user;


import com.portofolio.demo.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryService {

    User save(User usesr);

    void deleteById(Long id);

    Optional<User> getById(Long id);

    List<User> getAll();
}
