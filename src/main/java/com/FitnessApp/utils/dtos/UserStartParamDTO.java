package com.FitnessApp.utils.dtos;

import java.util.Objects;

public class UserStartParamDTO {

    private Long id;
    private Long exerciseID;
    private Long programTemplateID;
    private Integer initialWeight;

    public UserStartParamDTO() {
    }

    public UserStartParamDTO(Long id, Long exerciseID, Long programTemplateID, Integer initialWeight) {
        this.id = id;
        this.exerciseID = exerciseID;
        this.programTemplateID = programTemplateID;
        this.initialWeight = initialWeight;
    }

    public Integer getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(Integer initialWeight) {
        this.initialWeight = initialWeight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(Long exerciseID) {
        this.exerciseID = exerciseID;
    }

    public Long getProgramTemplateID() {
        return programTemplateID;
    }

    public void setProgramTemplateID(Long programTemplateID) {
        this.programTemplateID = programTemplateID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStartParamDTO)) return false;
        UserStartParamDTO that = (UserStartParamDTO) o;
        return id.equals(that.id) &&
                exerciseID.equals(that.exerciseID) &&
                programTemplateID.equals(that.programTemplateID) &&
                initialWeight.equals(that.initialWeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exerciseID, programTemplateID, initialWeight);
    }

    @Override
    public String toString() {
        return "UserStartParamDTO{" +
                "id=" + id +
                ", exerciseID=" + exerciseID +
                ", programTemplateID=" + programTemplateID +
                ", initialWeight=" + initialWeight +
                '}';
    }
}
