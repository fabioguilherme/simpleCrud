package com.portofolio.demo.infrastructure.item;

import com.portofolio.demo.domain.Item.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
}
