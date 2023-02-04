package com.portofolio.demo.infrastructure.persistence.order;

import com.portofolio.demo.domain.order.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {


}
