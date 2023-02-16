package com.portofolio.demo.userInterface.controller.user;

import com.portofolio.demo.aplication.user.UserApplicationService;
import com.portofolio.demo.aplication.user.model.CreateUserRequest;
import com.portofolio.demo.aplication.user.model.UserDto;
import com.portofolio.demo.models.json.user.CreateUserRequestJson;
import com.portofolio.demo.models.json.user.UpdateUserEmailRequestJson;
import com.portofolio.demo.models.json.user.User;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import com.portofolio.demo.userInterface.controller.MainController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/user")
public class UserController extends MainController {

    private final UserApplicationService service;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserApplicationService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody CreateUserRequestJson request) {

        CreateUserRequest serviceRequest = CreateUserRequest.Builder.with().name(request.getName()).email(request.getEmail()).build();

        UserDto dto = service.save(serviceRequest);

        log.info("Created user.");

        return fromDtoToJson(dto);
    }


    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private User getUser(@PathVariable("userId") Long userId) {

        Optional<UserDto> optionalDto = service.getById(userId);

        if (optionalDto.isEmpty()) {
            log.info("User with id " + userId + " not found");
            throw new ResourceNotFoundException("User with id " + userId + " not found", null);
        }

        log.info("User found with id " + userId);

        return fromDtoToJson(optionalDto.get());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("userId") Long userId) {
        service.deleteById(userId);
        log.info("User deleted with id " + userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {

        List<UserDto> userDtos = service.getAll();

        log.info("Search ended.");

        return userDtos.stream().map(this::fromDtoToJson).collect(Collectors.toList());
    }

    @PatchMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User updateUserEmail(@Valid @RequestBody UpdateUserEmailRequestJson request, @PathVariable("userId") Long userId) {

        UserDto dto = service.updateUserEmail(userId, request.getEmail());

        log.info(String.format("User with id %d updated", userId));

        return fromDtoToJson(dto);
    }

    private User fromDtoToJson(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setUri(userDto.getUri());

        return user;
    }
}
