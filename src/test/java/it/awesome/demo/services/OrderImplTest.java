package it.awesome.demo.services;

import it.awesome.demo.entities.Order;
import it.awesome.demo.entities.enums.StatusEnum;
import it.awesome.demo.exceptions.ActiveOrderNotFoundException;
import it.awesome.demo.exceptions.IdNotFounException;
import it.awesome.demo.repositories.OrderRepository;
import it.awesome.demo.services.impl.OrderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.findAll();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void findById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void create() throws Exception {
        Order order = new Order();
        order.setOrderElements(new HashSet<>());

        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.create(order);

        assertNotNull(result);
        assertEquals(StatusEnum.NEW, result.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void update() throws Exception {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.update(order);

        assertNotNull(result);
        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void delete() throws Exception {
        Long id = 1L;
        doNothing().when(orderRepository).deleteById(id);

        orderService.delete(id);

        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    void findActiveOrder() {
        Order order = new Order();
        order.setStatus(StatusEnum.NEW);
        when(orderRepository.findFirstByStatusInOrderByIdAsc(anyList())).thenReturn(Optional.of(order));

        Order result = orderService.findActiveOrder();

        assertNotNull(result);
        verify(orderRepository, times(1)).findFirstByStatusInOrderByIdAsc(anyList());
    }

    @Test
    void continueProcessFromDoing() {
        Order order = new Order();
        order.setStatus(StatusEnum.DOING);
        when(orderRepository.findFirstByStatusInOrderByIdAsc(anyList())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.continueProcess();

        assertEquals(StatusEnum.READY, result.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void continueProcessFromNew() {
        Order order = new Order();
        order.setStatus(StatusEnum.NEW);
        when(orderRepository.findFirstByStatusInOrderByIdAsc(anyList())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.continueProcess();

        assertEquals(StatusEnum.DOING, result.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void findByCode() {
        Order order = new Order();
        order.setOrderCode("ABC123");
        when(orderRepository.findByOrderCode("ABC123")).thenReturn(order);

        Order result = orderService.findByCode("ABC123");

        assertNotNull(result);
        assertEquals("ABC123", result.getOrderCode());
        verify(orderRepository, times(1)).findByOrderCode("ABC123");
    }

    @Test
    void findByIdIfIdIsNotPresent() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IdNotFounException.class, () -> {
            orderService.findById(1L);
        });

        assertEquals("Id not found", exception.getMessage());
        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, never()).findById(1L);
    }

    @Test
    void findActiveOrderIfDoesNotExist() {
        when(orderRepository.findFirstByStatusInOrderByIdAsc(anyList())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ActiveOrderNotFoundException.class, () -> {
            orderService.findActiveOrder();
        });

        assertEquals("Nessun ordine attivo presente!", exception.getMessage());
        verify(orderRepository, times(1)).findFirstByStatusInOrderByIdAsc(anyList());
    }
}
