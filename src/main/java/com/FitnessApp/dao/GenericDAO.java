package com.FitnessApp.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenericDAO {

    <T> Optional<List<T>> getByParameter(Class<T> clazz, String parameterName, String parameterValue);

    <T> Optional<List<T>> getAll(Class<T> clazz);

    <T> Optional<T> getByID(Class<T> clazz, Number number);

    <T> void save(T obj);

    <T> T update(T obj);

    <T> void delete(T obj);

    <T> void deleteById(Class clazz, Number id);

    <T> Optional<List<T>> getAllPagination(Class<T> clazz, int firstRes, int maxRes);
}
