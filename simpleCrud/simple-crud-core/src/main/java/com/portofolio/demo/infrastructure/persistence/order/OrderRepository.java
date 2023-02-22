package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.domain.order.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT o from Order o where o.status not like 'DONE'")
    List<Order> getOrdersNotDone();

}
