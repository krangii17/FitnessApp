package com.FitnessApp.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @Column(name = "ex_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exID;

    @NotEmpty
    @Column(name = "exercise_name")
    private String exerciseName;

    @NotEmpty
    @Column(name = "description", length = 400)
    private String description;

    @Column(name = "exercise_effectiveness")
    private Integer exerciseEffectiveness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private Training training;

    @OneToMany(mappedBy = "exercise",
            orphanRemoval = true
    )
    private List<com.FitnessApp.model.MusclesGroup> MusclesGroup = new ArrayList<>();

    @OneToMany(mappedBy = "exercise",
            orphanRemoval = true
    )
    private List<Restriction> restrictions = new ArrayList<>();

    @OneToMany(mappedBy = "exercise",
            orphanRemoval = true
    )
    private List<UserStartParam> userStartParams = new ArrayList<>();

    public Exercise() {
    }

    public Exercise(@NotEmpty String exerciseName, @NotEmpty String description,
                    Integer exerciseEffectiveness) {
        this.exerciseName = exerciseName;
        this.description = description;
        this.exerciseEffectiveness = exerciseEffectiveness;
    }

    public Long getExID() {
        return exID;
    }

    public void setExID(Long exID) {
        this.exID = exID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public List<com.FitnessApp.model.MusclesGroup> getMusclesGroup() {
        return MusclesGroup;
    }

    public void setMusclesGroup(List<com.FitnessApp.model.MusclesGroup> musclesGroup) {
        MusclesGroup = musclesGroup;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public Integer getExerciseEffectiveness() {
        return exerciseEffectiveness;
    }

    public void setExerciseEffectiveness(Integer exerciseEffectiveness) {
        this.exerciseEffectiveness = exerciseEffectiveness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise)) return false;
        Exercise exercise = (Exercise) o;
        return exID.equals(exercise.exID) &&
                exerciseName.equals(exercise.exerciseName) &&
                description.equals(exercise.description) &&
                training.equals(exercise.training) &&
                MusclesGroup.equals(exercise.MusclesGroup) &&
                restrictions.equals(exercise.restrictions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exID, exerciseName, description, training, MusclesGroup, restrictions);
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exID=" + exID +
                ", exerciseName='" + exerciseName + '\'' +
                ", description='" + description + '\'' +
                ", training=" + training +
                ", exerciseEffectiveness=" + exerciseEffectiveness +
                ", MusclesGroup=" + MusclesGroup +
                ", restrictions=" + restrictions +
                '}';
    }
}
