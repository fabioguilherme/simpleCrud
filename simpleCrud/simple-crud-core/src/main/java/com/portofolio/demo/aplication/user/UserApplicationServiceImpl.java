package com.portofolio.demo.aplication.user;

import com.portofolio.demo.aplication.user.model.CreateUserRequest;
import com.portofolio.demo.aplication.user.model.UserDto;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.domain.user.UserDomainService;
import com.portofolio.demo.infrastructure.persistence.user.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final String URI_BASE;
    private final String USER_URI_BASE = "/user/";
    private final UserRepositoryService repositoryService;
    private final UserDomainService domainService;

    @Autowired
    public UserApplicationServiceImpl(UserRepositoryService repositoryService, UserDomainService domainService, @Value("${uri.base}") String URI_BASE) {
        this.URI_BASE = URI_BASE;
        this.repositoryService = repositoryService;
        this.domainService = domainService;
    }

    @Override
    public UserDto save(CreateUserRequest request) {

        User userToPersist = domainService.createUser(request.getName(), request.getEmail());
        User userPersisted = repositoryService.save(userToPersist);

        return fromEntity(userPersisted);
    }

    @Override
    public void deleteById(Long id) {
        repositoryService.deleteById(id);
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        Optional<User> userOptional = repositoryService.getById(id);

        return userOptional.map(this::fromEntity);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> list = repositoryService.getAll();

        return list.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private UserDto fromEntity(User entity) {
        return UserDto.Builder.with().name(entity.getName()).email(entity.getEmail()).id(entity.getId()).uri(URI_BASE + USER_URI_BASE + entity.getId()).build();
    }
}
