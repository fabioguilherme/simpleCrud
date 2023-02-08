package com.portofolio.demo.infrastructure.persistence.item;

import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemRepositoryServiceImpl implements ItemRepositoryService {

    private final ItemRepository repository;

    @Autowired
    public ItemRepositoryServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public Item save(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item can not be null");
        }

        return repository.save(item);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id, null);
        }

        repository.deleteById(id);
    }

    @Override
    public Optional<Item> getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }

        return repository.findById(id);
    }

    @Override
    public List<Item> getAll() {
        Iterable<Item> iterable = repository.findAll();

        List<Item> list = new ArrayList<Item>();
        iterable.forEach(list::add);

        return list;
    }
}
