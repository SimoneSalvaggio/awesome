package it.awesome.demo.controllers.mappers;

import it.awesome.demo.controllers.dtos.ElementDto;
import it.awesome.demo.controllers.dtos.OrderDto;
import it.awesome.demo.entities.Order;
import it.awesome.demo.entities.OrderElement;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrderDtoMapper {

    public OrderDto entityToDto(Order entity){
        OrderDto dto = new OrderDto();
        dto.setStatus(entity.getStatus());
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        Set<ElementDto> elemDtos = new HashSet<>();
        for (OrderElement orderElement : entity.getOrderElements()){
            ElementDto elemDto = new ElementDto();
            elemDto.setName(orderElement.getElement().getName());
            elemDto.setDescription(orderElement.getElement().getDescription());
            elemDto.setQuantity(orderElement.getQuantity());
            elemDtos.add(elemDto);
        }
        dto.setElements(elemDtos);
        return dto;
    }
}
