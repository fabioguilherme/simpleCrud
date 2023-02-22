package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.domain.order.Order;
import com.portofolio.demo.domain.order.OrderStatus;
import com.portofolio.demo.domain.user.User;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderRepositoryServiceImpl implements OrderRepositoryService {

    private final OrderRepository repository;

    private final EntityManager entityManager;

    @Autowired
    public OrderRepositoryServiceImpl(OrderRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public Order save(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order can not be null");
        }

        return repository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id, null);
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<Order> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        return repository.findById(id);
    }

    @Override
    public List<Order> getAll(Long userId, OrderStatus status) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);

        Root<Order> root = cq.from(Order.class);

        List<Predicate> predicates = new ArrayList<>();

        if (userId != null) {

            Join<Order, User> user = root.join("user");

            predicates.add(cb.equal(user.get("id"), userId));
        }

        if (status != null) {
            predicates.add(cb.equal(root.get("status"), status));
        }

        if (!predicates.isEmpty())
            cq.where(predicates.toArray(new Predicate[0]));


        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Order> getOrdersNotDone() {
        return repository.getOrdersNotDone();
    }
}
