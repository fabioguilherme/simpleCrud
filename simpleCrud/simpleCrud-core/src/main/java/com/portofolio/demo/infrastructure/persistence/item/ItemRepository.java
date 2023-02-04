package com.portofolio.demo.infrastructure.persistence.item;

import com.portofolio.demo.domain.item.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}
