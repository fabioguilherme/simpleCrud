package com.portofolio.demo.aplication.item;

import com.portofolio.demo.aplication.item.model.CreateItemRequest;
import com.portofolio.demo.aplication.item.model.ItemDto;
import com.portofolio.demo.domain.item.Item;
import com.portofolio.demo.domain.item.ItemDomainService;
import com.portofolio.demo.infrastructure.persistence.item.ItemRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemApplicationServiceImpl implements ItemApplicationService {

    private final String uri_base;
    private final String ITEM_URI_BASE = "/item/";

    private final ItemDomainService itemDomainService;
    private final ItemRepositoryService itemRepositoryService;

    @Autowired
    public ItemApplicationServiceImpl(ItemDomainService itemDomainService, ItemRepositoryService itemRepositoryService, @Value("${uri.base}") String uriBase) {
        this.uri_base = uriBase;
        this.itemDomainService = itemDomainService;
        this.itemRepositoryService = itemRepositoryService;
    }

    @Override
    public ItemDto save(CreateItemRequest request) {

        Item itemToPersist = itemDomainService.createItem(request.getName());
        Item itemPersisted = itemRepositoryService.save(itemToPersist);

        return ItemDto.Builder.with().name(itemPersisted.getName()).id(itemPersisted.getId()).uri(uri_base + ITEM_URI_BASE + itemToPersist.getId()).build();
    }

    @Override
    public void deleteById(Long id) {
        itemRepositoryService.deleteById(id);
    }

    @Override
    public Optional<ItemDto> getById(Long id) {

        Optional<Item> itemOptional = itemRepositoryService.getById(id);

        if (itemOptional.isEmpty()) {
            return Optional.empty();
        }

        Item item = itemOptional.get();

        return Optional.of(ItemDto.Builder.with().id(item.getId()).name(item.getName()).uri(uri_base + ITEM_URI_BASE + item.getId()).build());
    }

    @Override
    public List<ItemDto> getAll() {
        return null;
    }
}
