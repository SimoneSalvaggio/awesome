package it.awesome.demo.services;

import it.awesome.demo.entities.Element;
import it.awesome.demo.exceptions.CannotUpdateEntityException;
import it.awesome.demo.exceptions.IdNotFounException;
import it.awesome.demo.repositories.ElementRepository;
import it.awesome.demo.services.impl.ElementImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ElementImplTest {

    @Mock
    private ElementRepository elementRepository;

    @InjectMocks
    private ElementImpl elementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Element element1 = new Element();
        Element element2 = new Element();
        when(elementRepository.findAll()).thenReturn(Arrays.asList(element1, element2));

        List<Element> elements = elementService.findAll();

        assertNotNull(elements);
        assertEquals(2, elements.size());
        verify(elementRepository, times(1)).findAll();
    }

    @Test
    void findById() throws Exception {
        Element element = new Element();
        element.setId(1L);
        when(elementRepository.existsById(1L)).thenReturn(true);
        when(elementRepository.findById(1L)).thenReturn(Optional.of(element));

        Element result = elementService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(elementRepository, times(1)).existsById(1L);
        verify(elementRepository, times(1)).findById(1L);
    }

    @Test
    void create() throws Exception {
        Element element = new Element();
        when(elementRepository.save(element)).thenReturn(element);

        Element result = elementService.create(element);

        assertNotNull(result);
        verify(elementRepository, times(1)).save(element);
    }

    @Test
    void update() throws Exception {
        Element element = new Element();
        element.setId(1L);
        when(elementRepository.existsById(1L)).thenReturn(true);
        when(elementRepository.save(element)).thenReturn(element);

        Element result = elementService.update(element);

        assertNotNull(result);
        verify(elementRepository, times(1)).existsById(1L);
        verify(elementRepository, times(1)).save(element);
    }

    @Test
    void delete() throws Exception {
        Long id = 1L;
        doNothing().when(elementRepository).deleteById(id);

        elementService.delete(id);

        verify(elementRepository, times(1)).deleteById(id);
    }

    @Test
    void findByIdIfIdIsNotValid() {
        when(elementRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IdNotFounException.class, () -> {
            elementService.findById(1L);
        });

        assertEquals("Id not found", exception.getMessage());
        verify(elementRepository, times(1)).existsById(1L);
        verify(elementRepository, never()).findById(1L);
    }

    @Test
    void updateIfIdIsNull() {
        Element element = new Element();
        element.setId(null);

        Exception exception = assertThrows(CannotUpdateEntityException.class, () -> {
            elementService.update(element);
        });

        assertEquals("Impossible to update entity", exception.getMessage());
        verify(elementRepository, never()).existsById(any());
        verify(elementRepository, never()).save(any());
    }
}
