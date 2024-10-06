package it.awesome.demo.repositories;

import it.awesome.demo.entities.Order;
import it.awesome.demo.entities.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findFirstByStatusInOrderByIdAsc(List<StatusEnum> statuses);

    Order findByOrderCode(String code);
}
