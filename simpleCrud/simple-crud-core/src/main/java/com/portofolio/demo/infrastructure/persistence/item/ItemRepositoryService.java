package com.portofolio.demo.infrastructure.persistence.item;

import com.portofolio.demo.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryService {

    Item save(Item item);

    void deleteById(Long id);

    Optional<Item> getById(Long id);

    List<Item> getAll();
}
