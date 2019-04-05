package com.FitnessApp.dao;

import com.FitnessApp.model.UserProgram;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IUserProgramDAO extends GenericDAO {

    Optional<List<UserProgram>> getByUserId(Long idUser);

    Optional<List<UserProgram>> getByUserLogin(String loginUser);
}
