package com.FitnessApp.security;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Qualifier("genericDAOImpl")
    @Autowired
    private GenericDAO dao;

    @Override
    public UserDetails loadUserByUsername(String userLogin)
            throws UsernameNotFoundException, IllegalArgumentException {

        Optional<List<User>> user = dao.getByParameter(User.class, "email", userLogin);

        if (!user.isPresent() || (user.get().size() == 0)) {
            throw new UsernameNotFoundException(userLogin);
        } else if (user.get().size() > 1) {
            throw new IllegalArgumentException("Detected more than one user");
        } else {
            return new UserDetailsImpl(user.get().get(0));
        }
    }
}
