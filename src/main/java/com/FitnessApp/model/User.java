package com.FitnessApp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = -5499820974222567030L;

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Column(name = "secret_question")
    private String secretQuestion;
    @Column(name = "secret_answer")
    private String secretAnswer;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private UserParameters userParameters;

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public State getState() {
        return state;
    }

    public UserParameters getUserParameters() {
        return userParameters;
    }

    public void setUserParameters(UserParameters userParameters) {
        this.userParameters = userParameters;
    }

    public void setState(State state) {
        this.state = state;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getSecretQuestion(), user.getSecretQuestion()) &&
                Objects.equals(getSecretAnswer(), user.getSecretAnswer()) &&
                Objects.equals(getRole(), user.getRole()) &&
                getState() == user.getState() &&
                Objects.equals(getUserParameters(), user.getUserParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getUsername(),
                getPassword(),
                getEmail(),
                getSecretQuestion(),
                getSecretAnswer(),
                getRole(),
                getState(),
                getUserParameters());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", secretQuestion='" + secretQuestion + '\'' +
                ", secretAnswer='" + secretAnswer + '\'' +
                ", role=" + role +
                ", state=" + state +
                ", userParameters=" + userParameters +
                '}';
    }
}
