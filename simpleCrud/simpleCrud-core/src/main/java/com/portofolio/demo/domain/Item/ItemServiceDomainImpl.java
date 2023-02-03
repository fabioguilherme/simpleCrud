package com.portofolio.demo.domain.Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceDomainImpl implements ItemDomainService {

    Logger log = LoggerFactory.getLogger(ItemServiceDomainImpl.class);

    @Override
    public Item createItem(String name) {

        log.info("Creating a new item with name: " + name);
        return Item.withName(name);
    }
}
