package org.kylecodes.gm.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface ApiService<T> {

    List<T> findAll();
//    T save(T entity);
    Optional<T> findById(Long id);

    T create(@RequestBody T obj);

    void delete(@PathVariable Long id);
}
