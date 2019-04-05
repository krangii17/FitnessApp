package com.FitnessApp.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_COACH, ROLE_ADMIN, ROLE_GUEST;

    public static List<String> getAllRoles() {
        List<String> roles = new ArrayList<>();
        for (Role role : Role.values()) {
            if (role != ROLE_GUEST) {
                roles.add(role.getAuthority());
            }
        }
        return roles;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
