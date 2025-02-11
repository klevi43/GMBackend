package org.kylecodes.gm.services;

import java.util.List;
import java.util.Optional;

public interface ApiService<T> {

    List<T> findAll();
//    T save(T entity);
    Optional<T> findById(Long id);
}
