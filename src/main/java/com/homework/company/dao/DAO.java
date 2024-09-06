package com.homework.company.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    Status save(T t);
    List<T> all();
    Optional<T> findById(Long id);
    Status update(T t);
    Status delete(Long id);
}
