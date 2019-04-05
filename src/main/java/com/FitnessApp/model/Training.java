package com.FitnessApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "training")
public class Training {
    @Id
    @Column(name = "train_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainID;

    @NotEmpty
    @Column(name = "time")
    private Integer time;

    @NotEmpty
    @Column(name = "projectile_weight")
    private Integer projectileWeight;

    @NotEmpty
    @Column(name = "iteration")
    private Integer iteration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "training",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Exercise> exercises = new ArrayList<>();

    public Long getTrainID() {
        return trainID;
    }

    public void setTrainID(Long trainID) {
        this.trainID = trainID;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getProjectileWeight() {
        return projectileWeight;
    }

    public void setProjectileWeight(Integer projectileWeight) {
        this.projectileWeight = projectileWeight;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Training)) return false;
        Training training = (Training) o;
        return trainID.equals(training.trainID) &&
                time.equals(training.time) &&
                projectileWeight.equals(training.projectileWeight) &&
                iteration.equals(training.iteration) &&
                schedule.equals(training.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainID, time, projectileWeight, iteration, schedule);
    }

    @Override
    public String toString() {
        return "Training{" +
                "trainID=" + trainID +
                ", time=" + time +
                ", projectileWeight=" + projectileWeight +
                ", iteration=" + iteration +
                ", schedule=" + schedule +
                '}';
    }
}

