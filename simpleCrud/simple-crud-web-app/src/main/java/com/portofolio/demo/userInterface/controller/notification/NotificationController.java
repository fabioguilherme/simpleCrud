package com.portofolio.demo.userInterface.controller.notification;

import com.portofolio.demo.aplication.notification.NotificationApplicationService;
import com.portofolio.demo.aplication.notification.model.CreateNotificationRequest;
import com.portofolio.demo.aplication.notification.model.NotificationDto;
import com.portofolio.demo.models.json.notification.CreateNotificationRequestJson;
import com.portofolio.demo.models.json.notification.Notification;
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
@RequestMapping(value = "api/notification")
public class NotificationController extends MainController {

    private final NotificationApplicationService service;

    private final Logger log = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    public NotificationController(NotificationApplicationService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Notification createNotification(@Valid @RequestBody CreateNotificationRequestJson request) {

        CreateNotificationRequest serviceRequest = CreateNotificationRequest.Builder.with().message(request.getMessage()).userId(request.getUserId()).build();

        NotificationDto dto = service.save(serviceRequest);

        log.info("Created notification.");

        return fromDtoToJson(dto);
    }

    @GetMapping(path = "/{notificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private Notification getNotification(@PathVariable("notificationId") Long notificationId) {

        Optional<NotificationDto> optionalDto = service.getById(notificationId);

        if (optionalDto.isEmpty()) {
            log.info("Notification with id " + notificationId + " not found");
            throw new ResourceNotFoundException("Notification with id " + notificationId + " not found", null);
        }

        log.info("Notification found with id " + notificationId);

        return fromDtoToJson(optionalDto.get());
    }

    //TODO get notification by user email

    @DeleteMapping("/{notificationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotificationById(@PathVariable("notificationId") Long notificationId) {
        service.deleteById(notificationId);
        log.info("Notification deleted with id: " + notificationId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Notification> getAllNotifications() {

        List<NotificationDto> notificationDtos = service.getAll();

        log.info("Search end.");

        return notificationDtos.stream().map(this::fromDtoToJson).collect(Collectors.toList());
    }

    private Notification fromDtoToJson(NotificationDto notificationDto) {
        Notification notification = new Notification();

        notification.setId(notificationDto.getId());
        notification.setUserEmail(notificationDto.getUserEmail());
        notification.setUserName(notificationDto.getUserName());
        notification.setMessage(notificationDto.getMessage());
        notification.setUri(notificationDto.getUri());
        notification.setCreationDate(notificationDto.getCreationDate());


        return notification;
    }

}
