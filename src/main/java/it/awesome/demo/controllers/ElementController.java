package it.awesome.demo.controllers;

import it.awesome.demo.entities.Element;
import it.awesome.demo.services.ElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/elements")
public class ElementController {

    @Autowired
    ElementService elementService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Element>> findAll() {
        log.info("ElementController - findAll");
        return new ResponseEntity<>(elementService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Element> findById(@PathVariable(value = "id") Long id) throws Exception {
        log.info("ElementController - findById - " + id);
        return new ResponseEntity<>(elementService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Element> create(@RequestBody Element object) throws Exception {
        log.info("ElementController - create");
        return new ResponseEntity<>(elementService.create(object), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Element> update(@RequestBody Element object) throws Exception {
        log.info("ElementController - update");
        return new ResponseEntity<>(elementService.update(object), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") Long id) throws Exception {
        log.info("ElementController - delete - " + id);
        elementService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
