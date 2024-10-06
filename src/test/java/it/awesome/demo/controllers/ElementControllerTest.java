package it.awesome.demo.controllers;

import it.awesome.demo.entities.Element;
import it.awesome.demo.services.ElementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ElementControllerTest {

    @Mock
    private ElementService elementService;

    @InjectMocks
    private ElementController elementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Element element1 = new Element();
        Element element2 = new Element();
        when(elementService.findAll()).thenReturn(Arrays.asList(element1, element2));

        ResponseEntity<List<Element>> response = elementController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(elementService, times(1)).findAll();
    }

    @Test
    void findById() throws Exception {
        Element element = new Element();
        element.setId(1L);
        when(elementService.findById(1L)).thenReturn(element);

        ResponseEntity<Element> response = elementController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(elementService, times(1)).findById(1L);
    }

    @Test
    void create() throws Exception {
        Element element = new Element();
        when(elementService.create(element)).thenReturn(element);

        ResponseEntity<Element> response = elementController.create(element);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(element, response.getBody());
        verify(elementService, times(1)).create(element);
    }

    @Test
    void update() throws Exception {
        Element element = new Element();
        element.setId(1L);
        when(elementService.update(element)).thenReturn(element);

        ResponseEntity<Element> response = elementController.update(element);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(elementService, times(1)).update(element);
    }

    @Test
    void delete() throws Exception {
        doNothing().when(elementService).delete(1L);

        ResponseEntity response = elementController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(elementService, times(1)).delete(1L);
    }
}
