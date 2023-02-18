package com.portofolio.demo.aplication.notification;

import com.portofolio.demo.aplication.notification.model.CreateNotificationRequest;
import com.portofolio.demo.aplication.notification.model.NotificationDto;
import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.notification.NotificationDomainService;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.infrastructure.persistence.notification.NotificationRepositoryService;
import com.portofolio.demo.infrastructure.persistence.user.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationApplicationServiceImpl implements NotificationApplicationService {

    private final String URI_BASE;
    private final String NOTIFICATION_URI_BASE = "/notification/";
    private final UserRepositoryService userRepositoryService;
    private final NotificationRepositoryService repositoryService;
    private final NotificationDomainService domainService;

    @Autowired
    public NotificationApplicationServiceImpl(UserRepositoryService userRepositoryService, NotificationRepositoryService repositoryService, NotificationDomainService domainService, @Value("${uri.base}") String uriBase) {
        this.userRepositoryService = userRepositoryService;
        this.repositoryService = repositoryService;
        this.domainService = domainService;
        this.URI_BASE = uriBase;
    }

    @Override
    public NotificationDto save(CreateNotificationRequest request) {


        Optional<User> userOptional = userRepositoryService.getById(request.getUserId());
        Notification notificationToPersist = domainService.createNotification(userOptional.orElse(null), request.getMessage());
        Notification notificationToPersisted = repositoryService.save(notificationToPersist);


        return fromEntity(notificationToPersisted);
    }


    @Override
    public void deleteById(Long id) {
        repositoryService.deleteById(id);
    }

    @Override
    public Optional<NotificationDto> getById(Long id) {
        return repositoryService.getById(id).map(this::fromEntity);
    }


    @Override
    public List<NotificationDto> getAll(Long userId) {
        List<Notification> list = repositoryService.getAll(userId);

        return list.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private NotificationDto fromEntity(Notification notificationToPersist) {
        return NotificationDto.Builder.with()
                .id(notificationToPersist.getId())
                .message(notificationToPersist.getMessage())
                .uri(URI_BASE + NOTIFICATION_URI_BASE + notificationToPersist.getId())
                .userEmail(notificationToPersist.getUser().getEmail())
                .userName(notificationToPersist.getUser().getName()).build();
    }

}
