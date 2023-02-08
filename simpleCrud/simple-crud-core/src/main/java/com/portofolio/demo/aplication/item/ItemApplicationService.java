package com.portofolio.demo.aplication.item;

import com.portofolio.demo.aplication.item.model.CreateItemRequest;
import com.portofolio.demo.aplication.item.model.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemApplicationService {

    ItemDto save(CreateItemRequest request);

    void deleteById(Long id);

    Optional<ItemDto> getById(Long id);

    List<ItemDto> getAll();
}
