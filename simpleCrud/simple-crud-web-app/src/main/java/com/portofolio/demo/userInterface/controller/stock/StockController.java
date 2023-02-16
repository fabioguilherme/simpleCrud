package com.portofolio.demo.userInterface.controller.stock;

import com.portofolio.demo.aplication.stock.StockApplicationService;
import com.portofolio.demo.aplication.stock.model.CreateStockRequest;
import com.portofolio.demo.aplication.stock.model.StockDto;
import com.portofolio.demo.models.json.stock.CreateStockRequestJson;
import com.portofolio.demo.models.json.stock.Stock;
import com.portofolio.demo.models.json.stock.UpdateStockRequestJson;
import com.portofolio.demo.shared.errors.ResourceNotFoundException;
import com.portofolio.demo.userInterface.controller.MainController;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/stock")
public class StockController extends MainController {

    private final StockApplicationService service;

    private final Logger log = LoggerFactory.getLogger(StockController.class);

    @Autowired
    public StockController(StockApplicationService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Stock createStock(@Valid @RequestBody CreateStockRequestJson request) {

        CreateStockRequest serviceRequest = CreateStockRequest.Builder.with().itemId(request.getItemId()).quantity(request.getQuantity()).build();

        StockDto dto = service.save(serviceRequest);

        log.info("Created stock.");

        return fromDtoToJson(dto);
    }


    @GetMapping(path = "/{stockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private Stock getStock(@PathVariable("stockId") Long stockId) {

        Optional<StockDto> optionalDto = service.getById(stockId);

        if (optionalDto.isEmpty()) {
            log.info("Stock with id " + stockId + " not found");
            throw new ResourceNotFoundException("Stock with id " + stockId + " not found", null);
        }

        log.info("Stock found with id " + stockId);

        return fromDtoToJson(optionalDto.get());
    }

    @DeleteMapping("/{stockId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStockById(@PathVariable("stockId") Long stockId) {
        service.deleteById(stockId);
        log.info("Stock deleted with id " + stockId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Stock> getAllStocks() {

        List<StockDto> stockDtos = service.getAll();

        log.info("Search ended.");

        return stockDtos.stream().map(this::fromDtoToJson).collect(Collectors.toList());
    }


    @PatchMapping(path = "/{stockId}/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private void addQuantityToStock(@PathVariable("stockId") Long stockId, @Valid @RequestBody UpdateStockRequestJson request) {

        service.addStockToItem(stockId, request.getQuantity());

        log.info("Added quantity to stock with id: " + stockId);
    }

    @PatchMapping(path = "/{stockId}/subtract", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private void subtractQuantityToStock(@PathVariable("stockId") Long stockId, @Valid @RequestBody UpdateStockRequestJson request) {

        service.subtractStockToItem(stockId, request.getQuantity());

        log.info("Added quantity to stock with id: " + stockId);
    }


    private Stock fromDtoToJson(StockDto stockDto) {

        Stock stock = new Stock();

        stock.setId(stockDto.getId());
        stock.setItemName(stockDto.getItemName());
        stock.setQuantity(stockDto.getQuantity());
        stock.setUri(stockDto.getUri());

        return stock;
    }
}
