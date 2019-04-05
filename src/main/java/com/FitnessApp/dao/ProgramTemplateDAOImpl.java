package com.FitnessApp.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@EnableTransactionManagement
public class ProgramTemplateDAOImpl extends GenericDAOImpl implements GenericDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<List<T>> getAll(Class<T> clazz) {

        return Optional.ofNullable(em.createQuery("from ProgramTemplate as p " +
                "left join fetch p.goal left join fetch p.exercise left join fetch p.user")
                .getResultList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getByID(Class<T> clazz, Number number) {

        return (Optional<T>) Optional.ofNullable(em.createQuery("select p from ProgramTemplate p " +
                "left join fetch p.goal left join fetch p.exercise left join fetch p.user " +
                "where p.id" + " = :parameterValue")
                .setParameter("parameterValue", number)
                .getResultList().get(0));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<List<T>> getByParameter(Class<T> clazz, String parameterName, String parameterValue) {

        return Optional.ofNullable(em.createQuery("select p from ProgramTemplate p " +
                "left join fetch p.goal left join fetch p.exercise left join fetch p.user " +
                "where p." + parameterName + " =: parameterValue")
                .setParameter("parameterValue", parameterValue)
                .getResultList());
    }
}
