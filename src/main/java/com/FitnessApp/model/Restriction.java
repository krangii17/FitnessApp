package com.FitnessApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "restrictions")
public class Restriction {
    @Id
    @Column(name = "rest_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restID;

//    @NotEmpty
    @Column(name = "restriction_name")
    private String name;

//    @NotEmpty
    @Column(name = "evaluation")
    private Integer evaluation;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_id")
    private Exercise exercise;

    @ManyToMany(mappedBy = "restrictions")
    private Set<UserParameters> UserParameters;

    public Long getRestID() {
        return restID;
    }

    public void setRestID(Long restID) {
        this.restID = restID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Set<com.FitnessApp.model.UserParameters> getUserParameters() {
        return UserParameters;
    }

    public void setUserParameters(Set<com.FitnessApp.model.UserParameters> userParameters) {
        UserParameters = userParameters;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restriction)) return false;
        Restriction that = (Restriction) o;
        return evaluation == that.evaluation &&
                restID.equals(that.restID) &&
                name.equals(that.name) &&
                state == that.state &&
                exercise.equals(that.exercise) &&
                UserParameters.equals(that.UserParameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restID, name, evaluation, state, exercise, UserParameters);
    }

    @Override
    public String toString() {
        return "Restriction{" +
                "restID=" + restID +
                ", name='" + name + '\'' +
                ", evaluation=" + evaluation +
                ", state=" + state +
                ", exercise=" + exercise +
                ", UserParameters=" + UserParameters +
                '}';
    }
}
