package com.FitnessApp.utils.dtos;

import com.FitnessApp.model.State;

import java.util.Objects;

/**
 * User data transfer objects.
 */
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String secretQuestion;
    private String secretAnswer;
    private State state;
    private String role;

    /**
     * Default
     */
    public UserDto() {
    }

    /**
     * Creates UserDto for sign up
     *
     * @param username user's login
     * @param password user's password
     * @param email    user's email
     */
    public UserDto(String username,
                   String email,
                   String password,
                   String secretQuestion,
                   String secretAnswer) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.secretAnswer = secretAnswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getId(), userDto.getId()) &&
                Objects.equals(getUsername(), userDto.getUsername()) &&
                Objects.equals(getEmail(), userDto.getEmail()) &&
                Objects.equals(getPassword(), userDto.getPassword()) &&
                Objects.equals(getSecretQuestion(), userDto.getSecretQuestion()) &&
                Objects.equals(getSecretAnswer(), userDto.getSecretAnswer()) &&
                getState() == userDto.getState() &&
                getRole() == userDto.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getEmail(), getPassword(), getSecretQuestion(), getSecretAnswer(), getState(), getRole());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", secretQuestion='" + secretQuestion + '\'' +
                ", secretAnswer='" + secretAnswer + '\'' +
                ", state=" + state +
                ", role='" + role + '\'' +
                '}';
    }
}
