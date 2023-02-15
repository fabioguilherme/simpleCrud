package com.portofolio.demo.userInterface.controller.item;


import com.portofolio.demo.aplication.item.ItemApplicationService;
import com.portofolio.demo.aplication.item.model.ItemDto;
import com.portofolio.demo.models.json.item.CreateItemRequest;
import com.portofolio.demo.models.json.item.Item;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import com.portofolio.demo.userInterface.controller.MainController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "api/item")
public class ItemController extends MainController {


    private final ItemApplicationService service;

    private Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    public ItemController(ItemApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createItem(@Valid @RequestBody CreateItemRequest request) {

        log.info("Creating item...");
    }


    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private Item getItem(@PathVariable("itemId") Long itemId) {

        Optional<ItemDto> optionalDto = service.getById(itemId);

        if (optionalDto.isEmpty()) {
            log.info("Item with id " + itemId + " not found");
            throw new ResourceNotFoundException("Item with id " + itemId + " not found", null);
        }

        log.info("Item found with id " + itemId);

        return fromDtoToJson(optionalDto.get());
    }

    private Item fromDtoToJson(ItemDto itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());

        return item;
    }
}
