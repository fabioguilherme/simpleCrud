package com.portofolio.demo.infrastructure.persistence.user;

import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository repository;

    @Autowired
    public UserRepositoryServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can not be null");
        }

        return repository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id, null);
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<User> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        return repository.findById(id);
    }

    @Override
    public List<User> getAll() {
        Iterable<User> iterable = repository.findAll();

        List<User> list = new ArrayList<User>();
        iterable.forEach(list::add);

        return list;
    }
}
