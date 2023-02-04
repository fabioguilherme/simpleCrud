package com.portofolio.demo.userInterface.item;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/item")
public class ItemController {


//    private final ItemDomainService service;

    private Logger log = LoggerFactory.getLogger(ItemController.class);

//    @Autowired
//    public ItemController(ItemDomainService service) {
//        this.service = service;
//    }

    @PostMapping
    public void createItem() {
        log.info("Creating item...");
    }
}
