package it.awesome.demo.controllers;

import it.awesome.demo.controllers.dtos.OrderDto;
import it.awesome.demo.controllers.mappers.OrderDtoMapper;
import it.awesome.demo.entities.Order;
import it.awesome.demo.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDtoMapper orderDtoMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> findAll() {
        log.info("OrderController - findAll");
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> findById(@PathVariable(value = "id") Long id) throws Exception {
        log.info("OrderController - findById - " + id);
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> create(@RequestBody Order object) throws Exception {
        log.info("OrderController - create");
        return new ResponseEntity<>(orderService.create(object), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> update(@RequestBody Order object) throws Exception {
        log.info("OrderController - update");
        return new ResponseEntity<>(orderService.update(object), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) throws Exception {
        log.info("OrderController - delete - " + id);
        orderService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> findByCode(@PathVariable(value = "code") String code) throws Exception {
        log.info("OrderController - findByCode - " + code);
        return new ResponseEntity<>(orderDtoMapper.entityToDto(orderService.findByCode(code)), HttpStatus.OK);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> findActive() throws Exception {
        log.info("OrderController - findActive");
        return new ResponseEntity<>(orderService.findActiveOrder(), HttpStatus.OK);
    }

    @PutMapping(value = "/process", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> continueProcess() throws Exception {
        log.info("OrderController - continueProcess");
        return new ResponseEntity<>(orderService.continueProcess(), HttpStatus.OK);
    }

}
