package com.FitnessApp.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IEntityService<T> {
    Optional<List<T>> getAll();

    List<T> getByParameter(String parameterName, String parameterValue);

    T getByID(Long id);

    boolean save(T savedObj);

    boolean update(Long id, T updatedObj);

    void delete(T deletedObj);

    void deleteById(Long id);
}
