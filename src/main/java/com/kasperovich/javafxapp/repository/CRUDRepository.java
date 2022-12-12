package com.kasperovich.javafxapp.repository;

import com.kasperovich.javafxapp.exception.RecurringEmailException;
import com.kasperovich.javafxapp.exception.RecurringOrgNameException;

import java.util.List;
import java.util.Optional;

public interface CRUDRepository<K, T> {

    int DEFAULT_FIND_ALL_LIMIT = 10;

    int DEFAULT_FIND_ALL_OFFSET = 0;

    T findById(K id);

    Optional<T> findOne(K id);

    List<T> findAll();

    List<T> findAll(Optional<Integer> limit, int offset);

    T create(T object) throws RecurringOrgNameException, RecurringEmailException;

    T update(T object);

    K delete(K id);
}