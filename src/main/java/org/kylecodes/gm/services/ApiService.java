package org.kylecodes.gm.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApiService<T> {

//    List<T> findAll();
//    T save(T entity);
    T findById(Long id);

    T create(@RequestBody T obj);

    void delete(@PathVariable Long id);

}
