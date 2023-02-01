package com.portofolio.demo.persistence;

import com.portofolio.demo.domain.Item.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
