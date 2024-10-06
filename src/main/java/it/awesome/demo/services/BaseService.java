package it.awesome.demo.services;

import java.util.List;

public interface BaseService<T, R> {

    public List<T> findAll();

    public T findById(R id) throws Exception;

    public T create(T object) throws Exception;

    public T update(T object) throws Exception;

    public void delete(R id) throws Exception;
}
