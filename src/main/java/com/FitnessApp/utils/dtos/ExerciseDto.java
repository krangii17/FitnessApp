package com.FitnessApp.utils.dtos;

public class ExerciseDto {

    private Long exID;
    private String exerciseName;
    private String description;
    private Integer exerciseEffectiveness;
    private Long exerciseId;

    public ExerciseDto() {
    }

    public ExerciseDto(Long exID, String exerciseName,
                       String description, Integer exerciseEffectiveness) {
        this.exID = exID;
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

    public Integer getExerciseEffectiveness() {
        return exerciseEffectiveness;
    }

    public void setExerciseEffectiveness(Integer exerciseEffectiveness) {
        this.exerciseEffectiveness = exerciseEffectiveness;
    }
}
