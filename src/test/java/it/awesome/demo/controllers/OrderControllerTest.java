package it.awesome.demo.controllers;

import it.awesome.demo.controllers.dtos.OrderDto;
import it.awesome.demo.controllers.mappers.OrderDtoMapper;
import it.awesome.demo.entities.Order;
import it.awesome.demo.services.ElementService;
import it.awesome.demo.services.OrderService;
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

class OrderControllerTest {

    @Mock
    private OrderService orderService;
    @Mock
    private OrderDtoMapper orderDtoMapper;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderService.findAll()).thenReturn(Arrays.asList(order1, order2));

        ResponseEntity<List<Order>> response = orderController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(orderService, times(1)).findAll();
    }

    @Test
    void findById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        when(orderService.findById(1L)).thenReturn(order);

        ResponseEntity<Order> response = orderController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(orderService, times(1)).findById(1L);
    }

    @Test
    void create() throws Exception {
        Order order = new Order();
        when(orderService.create(order)).thenReturn(order);

        ResponseEntity<Order> response = orderController.create(order);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).create(order);
    }

    @Test
    void update() throws Exception {
        Order order = new Order();
        order.setId(1L);
        when(orderService.update(order)).thenReturn(order);

        ResponseEntity<Order> response = orderController.update(order);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        verify(orderService, times(1)).update(order);
    }

    @Test
    void delete() throws Exception {
        doNothing().when(orderService).delete(1L);

        ResponseEntity response = orderController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).delete(1L);
    }

    @Test
    void findByCode() throws Exception {
        Order order = new Order();
        OrderDto orderDto = new OrderDto();
        when(orderService.findByCode("MARIOROSSI123")).thenReturn(order);
        when(orderDtoMapper.entityToDto(order)).thenReturn(orderDto);

        ResponseEntity<OrderDto> response = orderController.findByCode("MARIOROSSI123");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
        verify(orderService, times(1)).findByCode("MARIOROSSI123");
        verify(orderDtoMapper, times(1)).entityToDto(order);
    }

    @Test
    void findActiveOrder() throws Exception {
        Order order = new Order();
        when(orderService.findActiveOrder()).thenReturn(order);

        ResponseEntity<Order> response = orderController.findActive();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).findActiveOrder();
    }

    @Test
    void continueProcess() throws Exception {
        Order order = new Order();
        when(orderService.continueProcess()).thenReturn(order);

        ResponseEntity<Order> response = orderController.continueProcess();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
        verify(orderService, times(1)).continueProcess();
    }
}
