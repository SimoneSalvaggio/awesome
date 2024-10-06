package it.awesome.demo.services.impl;

import it.awesome.demo.entities.Element;
import it.awesome.demo.exceptions.CannotUpdateEntityException;
import it.awesome.demo.exceptions.IdNotFounException;
import it.awesome.demo.repositories.ElementRepository;
import it.awesome.demo.services.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementImpl implements ElementService {

    @Autowired
    ElementRepository elementRepository;

    @Override
    public List<Element> findAll() {
        return elementRepository.findAll();
    }

    @Override
    public Element findById(Long id) throws Exception {
        if(!elementRepository.existsById(id)){
            throw new IdNotFounException("Id not found");
        }
        return elementRepository.findById(id).get();
    }

    @Override
    public Element create(Element object) throws Exception {
        object.setId(null);
        Element element = elementRepository.save(object);
        return element;
    }

    @Override
    public Element update(Element object) throws Exception {
        if (object.getId() == null) {
            throw new CannotUpdateEntityException("Impossible to update entity");
        }
        if (!elementRepository.existsById(object.getId())) {
            throw new IdNotFounException("Id not found");
        }
        return elementRepository.save(object);
    }

    @Override
    public void delete(Long id) throws Exception {
    elementRepository.deleteById(id);
    }
}
