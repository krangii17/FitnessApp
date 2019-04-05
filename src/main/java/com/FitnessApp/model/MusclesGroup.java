package com.FitnessApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "group_of_muscles")
public class MusclesGroup {
    @Id
    @Column(name = "group_of_muscles_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_id")
    private Exercise exercise;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MusclesGroup)) return false;
        MusclesGroup that = (MusclesGroup) o;
        return ID.equals(that.ID) &&
                groupName.equals(that.groupName) &&
                exercise.equals(that.exercise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, groupName, exercise);
    }

    @Override
    public String toString() {
        return "MusclesGroup{" +
                "ID=" + ID +
                ", groupName='" + groupName + '\'' +
                ", exercise=" + exercise +
                '}';
    }
}

