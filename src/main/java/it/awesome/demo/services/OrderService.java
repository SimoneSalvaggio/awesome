package it.awesome.demo.services;

import it.awesome.demo.entities.Order;
import it.awesome.demo.exceptions.ActiveOrderNotFoundException;

public interface OrderService extends BaseService<Order, Long>{

    public Order findActiveOrder() throws ActiveOrderNotFoundException;

    public Order continueProcess();

    public Order findByCode(String code);
}
