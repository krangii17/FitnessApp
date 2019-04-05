package com.FitnessApp.utils.dtos;

import java.util.Objects;

public class RestrictionDTO {
    private Long id;
    private String name;
    private Integer evaluation;
    private String exerciseName;

    public RestrictionDTO() {
    }

    public RestrictionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RestrictionDTO(Long id, String name, Integer evaluation, String exerciseName) {
        this.id = id;
        this.name = name;
        this.evaluation = evaluation;
        this.exerciseName = exerciseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestrictionDTO)) return false;
        RestrictionDTO that = (RestrictionDTO) o;
        return evaluation == that.evaluation &&
                id.equals(that.id) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, evaluation);
    }

    @Override
    public String toString() {
        return "RestrictionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", evaluation=" + evaluation +
                '}';
    }
}
