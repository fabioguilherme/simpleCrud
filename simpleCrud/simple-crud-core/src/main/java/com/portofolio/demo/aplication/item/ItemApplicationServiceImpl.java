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
import java.util.stream.Collectors;

@Service
public class ItemApplicationServiceImpl implements ItemApplicationService {

    private final String URI_BASE;
    private final String ITEM_URI_BASE = "/item/";

    private final ItemDomainService itemDomainService;
    private final ItemRepositoryService itemRepositoryService;

    @Autowired
    public ItemApplicationServiceImpl(ItemDomainService itemDomainService, ItemRepositoryService itemRepositoryService, @Value("${uri.base}") String uriBase) {
        this.URI_BASE = uriBase;
        this.itemDomainService = itemDomainService;
        this.itemRepositoryService = itemRepositoryService;
    }

    @Override
    public ItemDto save(CreateItemRequest request) {

        Item itemToPersist = itemDomainService.createItem(request.getName());
        Item itemPersisted = itemRepositoryService.save(itemToPersist);

        return fromEntity(itemPersisted);
    }

    @Override
    public void deleteById(Long id) {
        itemRepositoryService.deleteById(id);
    }

    @Override
    public Optional<ItemDto> getById(Long id) {
        return itemRepositoryService.getById(id).map(this::fromEntity);
    }

    @Override
    public List<ItemDto> getAll() {

        List<Item> list = itemRepositoryService.getAll();

        return list.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private ItemDto fromEntity(Item item) {
        return ItemDto.Builder.with().id(item.getId()).name(item.getName()).uri(URI_BASE + ITEM_URI_BASE + item.getId()).build();
    }
}
