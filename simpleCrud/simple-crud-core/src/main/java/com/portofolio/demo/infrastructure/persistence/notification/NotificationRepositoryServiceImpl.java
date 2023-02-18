package com.portofolio.demo.infrastructure.persistence.notification;

import com.portofolio.demo.domain.notification.Notification;
import com.portofolio.demo.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationRepositoryServiceImpl implements NotificationRepositoryService {

    private final NotificationRepository repository;

    private final EntityManager entityManager;

    @Autowired
    public NotificationRepositoryServiceImpl(NotificationRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

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
    public List<Notification> getAll(Long userId) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);

        Root<Notification> book = cq.from(Notification.class);


        if (userId != null) {

            List<Predicate> predicates = new ArrayList<>();

            Join<Notification, User> user = book.join("user");

            predicates.add(cb.equal(user.get("id"), userId));

            cq.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(cq).getResultList();
    }
}
