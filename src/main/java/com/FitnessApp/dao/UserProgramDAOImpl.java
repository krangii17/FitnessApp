package com.FitnessApp.dao;

import com.FitnessApp.model.UserProgram;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
@EnableTransactionManagement
public class UserProgramDAOImpl extends GenericDAOImpl implements GenericDAO, IUserProgramDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<List<UserProgram>> getByUserId(Long idUser) {
        return Optional.ofNullable(em.createQuery("select e from UserProgram e " +
                "inner join e.user where e.user.id" + " = :parameterValue")
                .setParameter("parameterValue", idUser)
                .getResultList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<List<UserProgram>> getByUserLogin(String loginUser) {
        return Optional.ofNullable(em.createQuery("select e from UserProgram e " +
                "inner join e.user where e.user.email" + " =: parameterValue")
                .setParameter("parameterValue", loginUser)
                .getResultList());

    }
}
