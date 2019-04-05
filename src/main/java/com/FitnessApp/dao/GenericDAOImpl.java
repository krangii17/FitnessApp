package com.FitnessApp.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
@EnableTransactionManagement
public class GenericDAOImpl implements GenericDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public <T> Optional<List<T>> getByParameter(Class<T> clazz, String parameterName, String parameterValue) {
        return Optional.ofNullable(em.createQuery("from " + clazz.getName() + " as p where p." + parameterName + " =: parameterValue")
                .setParameter("parameterValue", parameterValue)
                .getResultList());
    }

    @Override
    public <T> Optional<List<T>> getAll(Class<T> clazz) {
        return Optional.ofNullable(em.createQuery("from " + clazz.getName()).getResultList());
    }

    @Override
    public <T> Optional<T> getByID(Class<T> clazz, Number number) {
        return Optional.ofNullable(em.find(clazz, number));
    }

    @Override
    public <T> void save(T obj) {
        em.persist(obj);
    }

    @Override
    public <T> T update(T obj) {
        return em.merge(obj);
    }

    @Override
    public <T> void delete(T obj) {
        em.remove(obj);
    }

    @Override
    public <T> void deleteById(Class clazz, Number id) {
        Optional<T> entity = (Optional<T>) getByID(clazz, id);
        if (entity.isPresent()) {
            delete(entity.get());
        } else {
            throw new IllegalArgumentException(id.toString());
        }
    }

    @Override
    public <T> Optional<List<T>> getAllPagination(Class<T> clazz, int firstRes, int maxRes) {
        return Optional.ofNullable(em.createQuery("from " + clazz.getName()).setFirstResult(firstRes).setMaxResults(maxRes).getResultList());
    }
}
