package it.awesome.demo.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.awesome.demo.entities.OrderElement;
import it.awesome.demo.entities.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderDto {

    private String name;
    private String address;
    private StatusEnum status;
    private Set<ElementDto> elements;

}
