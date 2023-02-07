package com.portofolio.demo.infrastructure.persistence.notification;

import com.portofolio.demo.domain.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationRepositoryServiceImpl implements NotificationRepositoryService {

    @Autowired
    private NotificationRepository repository;

    @Override
    public Notification save(Notification notification) {

        if (notification == null) {
            throw new IllegalArgumentException("Notification can not be null");
        }

        return repository.save(notification);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<Notification> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        return repository.findById(id);
    }

    @Override
    public List<Notification> getAll() {
        Iterable<Notification> iterable = repository.findAll();

        List<Notification> list = new ArrayList<Notification>();
        iterable.forEach(list::add);

        return list;
    }
}
