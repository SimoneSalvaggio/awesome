package it.awesome.demo.services.impl;

import it.awesome.demo.entities.Order;
import it.awesome.demo.entities.OrderElement;
import it.awesome.demo.entities.enums.StatusEnum;
import it.awesome.demo.exceptions.ActiveOrderNotFoundException;
import it.awesome.demo.exceptions.CannotUpdateEntityException;
import it.awesome.demo.exceptions.IdNotFounException;
import it.awesome.demo.repositories.OrderRepository;
import it.awesome.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) throws Exception {
        if(!orderRepository.existsById(id)){
            throw new IdNotFounException("Id not found");
        }
        return orderRepository.findById(id).get();
    }

    @Override
    public Order create(Order object) throws Exception {
        object.setId(null);
        object.setStatus(StatusEnum.NEW);
        for (OrderElement orderElement : object.getOrderElements()) {
            orderElement.setOrder(object); // Associa ogni OrderElement all'ordine
        }
        Order order = orderRepository.save(object);
        return order;
    }

    @Override
    public Order update(Order object) throws Exception {
        if(object.getId().equals(null)){
            throw new CannotUpdateEntityException("Impossible to update entity");
        }
        if(!orderRepository.existsById(object.getId())){
            throw new IdNotFounException("Id not found");
        }
        return orderRepository.save(object);
    }

    @Override
    public void delete(Long id) throws Exception {
    orderRepository.deleteById(id);
    }

    public Order findActiveOrder() {
        List<StatusEnum> activeStatuses = new ArrayList<>(Arrays.asList(StatusEnum.NEW, StatusEnum.DOING));
        Optional<Order> activeOrder = orderRepository.findFirstByStatusInOrderByIdAsc(activeStatuses);
        if (activeOrder.isPresent()){
            return activeOrder.get();
        }
        throw new ActiveOrderNotFoundException("Nessun ordine attivo presente!");
    }

    @Override
    public Order continueProcess() {
        Order order = findActiveOrder();
        if (order.getStatus().equals(StatusEnum.DOING)){
            order.setStatus(StatusEnum.READY);
        }
        if (order.getStatus().equals(StatusEnum.NEW)){
            order.setStatus(StatusEnum.DOING);
        }
        return orderRepository.save(order);
    }

    @Override
    public Order findByCode(String code) {
        return orderRepository.findByOrderCode(code);
    }
}
