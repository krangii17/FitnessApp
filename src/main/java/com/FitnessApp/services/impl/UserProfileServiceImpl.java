package com.FitnessApp.services.impl;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.User;
import com.FitnessApp.model.UserParameters;
import com.FitnessApp.services.UserProfileService;
import com.FitnessApp.utils.converters.UserProfileConverter;
import com.FitnessApp.utils.dtos.UserProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileConverter userProfileConverter;

    private final GenericDAO genericDAO;

    @Autowired
    public UserProfileServiceImpl(UserProfileConverter userProfileConverter,
                                  @Qualifier("genericDAOImpl") GenericDAO genericDAO) {

        this.userProfileConverter = userProfileConverter;
        this.genericDAO = genericDAO;
    }

    @Override
    public Optional<UserParameters> getUserParametersById(Long id) {
        return genericDAO.getByID(UserParameters.class, id);
    }

    @Override
    public UserProfileDTO getProfileData(User user) {

        return userProfileConverter.convertToProfileDTO(user);
    }

    @Override
    public boolean changeProfileData(UserProfileDTO dto, User user) {
        User newUser = userProfileConverter.convertToUser(dto);
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user = genericDAO.update(user);

        UserParameters newUserParameters = userProfileConverter.convertToUserParameters(dto);
        newUserParameters.setUser(user);

        if (isUserParametersEmpty(user.getUserParameters())) {
            newUserParameters.setID(user.getId());
            genericDAO.save(newUserParameters);
            return true;
        } else {
            newUserParameters.setID(user.getUserParameters().getID());
            Optional<UserParameters> updatedUserParameters =
                    Optional.ofNullable(genericDAO.update(newUserParameters));
            return updatedUserParameters.isPresent();
        }

    }

    @Override
    public void deleteById(Long id) throws ObjectNotFoundException{
        Optional<UserParameters> deletedObj = getUserParametersById(id);
        if (!deletedObj.isPresent()) {
            throw new ObjectNotFoundException(id.intValue());
        } else {
            genericDAO.delete(deletedObj.get());
        }
    }

    private boolean isUserParametersEmpty(UserParameters userParameters) {
        return userParameters == null;
    }
}
