package com.FitnessApp.utils.dtos.programTemplate;

import java.util.Objects;

public class ProgramTemplateTableDTO {
    private Long idProgramTemplate;
    private Integer numOfTrain;
    private Long exerciseId;
    private String exerciseName;
    private Integer percentOfMaximum;
    private Integer sets;
    private Integer reps;


    public ProgramTemplateTableDTO() {
    }

    public ProgramTemplateTableDTO(Long idProgramTemplate, Integer numOfTrain, Long exerciseId,
                                   String exerciseName, Integer percentOfMaximum, Integer sets,
                                   Integer reps) {

        this.idProgramTemplate = idProgramTemplate;
        this.numOfTrain = numOfTrain;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.percentOfMaximum = percentOfMaximum;
        this.sets = sets;
        this.reps = reps;
    }

    public Long getIdProgramTemplate() {
        return idProgramTemplate;
    }

    public void setIdProgramTemplate(Long idProgramTemplate) {
        this.idProgramTemplate = idProgramTemplate;
    }

    public Integer getNumOfTrain() {
        return numOfTrain;
    }

    public void setNumOfTrain(Integer numOfTrain) {
        this.numOfTrain = numOfTrain;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramTemplateTableDTO tableDTO = (ProgramTemplateTableDTO) o;
        return getIdProgramTemplate().equals(tableDTO.getIdProgramTemplate()) &&
                getNumOfTrain().equals(tableDTO.getNumOfTrain()) &&
                getExerciseId().equals(tableDTO.getExerciseId()) &&
                getExerciseName().equals(tableDTO.getExerciseName()) &&
                getPercentOfMaximum().equals(tableDTO.getPercentOfMaximum()) &&
                getSets().equals(tableDTO.getSets()) &&
                getReps().equals(tableDTO.getReps());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdProgramTemplate(), getNumOfTrain(), getExerciseId(), getExerciseName(),
                getPercentOfMaximum(), getSets(), getReps());
    }
}
