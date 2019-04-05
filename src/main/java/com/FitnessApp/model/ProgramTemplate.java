package com.FitnessApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "program_template")
public class ProgramTemplate {
    @Id
    @Column(name = "program_template_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "num_of_train")
    private Integer numOfTrain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    //@NotEmpty
    @Column(name = "percent_of_maximum")
    private Integer percentOfMaximum;

    //@NotEmpty
    @Column(name = "sets")
    private Integer sets;

    @Column(name = "reps")
    private Integer reps;

    //@NotEmpty
    @Column(name = "program_name")
    private String programName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    //@NotEmpty
    @Column(name = "number_of_days")
    private Integer numberOfDays;

    //@NotEmpty
    @Column(name = "is_base_program")
    private Boolean isBaseProgram;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ProgramTemplate(Long id, Integer numOfTrain, Exercise exercise, Integer percentOfMaximum,
                           Integer sets, Integer reps, String programName, Goal goal, Integer numberOfDays,
                           Boolean isBaseProgram, User user) {
        this.id = id;
        this.numOfTrain = numOfTrain;
        this.exercise = exercise;
        this.percentOfMaximum = percentOfMaximum;
        this.sets = sets;
        this.reps = reps;
        this.programName = programName;
        this.goal = goal;
        this.numberOfDays = numberOfDays;
        this.isBaseProgram = isBaseProgram;
        this.user = user;
    }

    public ProgramTemplate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumOfTrain() {
        return numOfTrain;
    }

    public void setNumOfTrain(Integer numOfTrain) {
        this.numOfTrain = numOfTrain;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getPercentOfMaximum() {
        return percentOfMaximum;
    }

    public void setPercentOfMaximum(Integer percentOfMaximum) {
        this.percentOfMaximum = percentOfMaximum;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Boolean getBaseProgram() {
        return isBaseProgram;
    }

    public void setBaseProgram(Boolean baseProgram) {
        isBaseProgram = baseProgram;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramTemplate that = (ProgramTemplate) o;
        return getId().equals(that.getId()) &&
                getNumOfTrain().equals(that.getNumOfTrain()) &&
                getExercise().equals(that.getExercise()) &&
                getPercentOfMaximum().equals(that.getPercentOfMaximum()) &&
                getSets().equals(that.getSets()) &&
                getReps().equals(that.getReps()) &&
                getProgramName().equals(that.getProgramName()) &&
                getGoal().equals(that.getGoal()) &&
                getNumberOfDays().equals(that.getNumberOfDays()) &&
                isBaseProgram.equals(that.isBaseProgram) &&
                getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumOfTrain(), getExercise(), getPercentOfMaximum(), getSets(), getReps(), getProgramName(), getGoal(), getNumberOfDays(), isBaseProgram, getUser());
    }
}
