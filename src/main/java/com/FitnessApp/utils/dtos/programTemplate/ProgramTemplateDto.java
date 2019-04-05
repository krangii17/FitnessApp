package com.FitnessApp.utils.dtos.programTemplate;

import com.FitnessApp.utils.dtos.GoalDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProgramTemplateDto {

    private String programName;
    private GoalDto goalDTO;
    private Integer numberOfDays;
    private Boolean isBaseProgram;
    private CoachDTO coachDTO;
    private List<ProgramTemplateTableDTO> tableDTOList;

    public ProgramTemplateDto() {
        tableDTOList = new ArrayList<>();
    }

    public ProgramTemplateDto(String programName, GoalDto goalDTO, Integer numberOfDays,
                              Boolean isBaseProgram, CoachDTO coachDTO,
                              List<ProgramTemplateTableDTO> tableDTOList) {

        this.programName = programName;
        this.goalDTO = goalDTO;
        this.numberOfDays = numberOfDays;
        this.isBaseProgram = isBaseProgram;
        this.coachDTO = coachDTO;
        this.tableDTOList = tableDTOList;
    }

    public ProgramTemplateDto(String programName, GoalDto goalDTO, Integer numberOfDays,
                              Boolean isBaseProgram, CoachDTO coachDTO) {
        this.programName = programName;
        this.goalDTO = goalDTO;
        this.numberOfDays = numberOfDays;
        this.isBaseProgram = isBaseProgram;
        this.coachDTO = coachDTO;
        this.tableDTOList = new ArrayList<>();
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public GoalDto getGoalDTO() {
        return goalDTO;
    }

    public void setGoalDTO(GoalDto goalDTO) {
        this.goalDTO = goalDTO;
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

    public CoachDTO getCoachDTO() {
        return coachDTO;
    }

    public void setCoachDTO(CoachDTO coachDTO) {
        this.coachDTO = coachDTO;
    }

    public List<ProgramTemplateTableDTO> getTableDTOList() {
        return tableDTOList;
    }

    public void setTableDTOList(List<ProgramTemplateTableDTO> tableDTOList) {
        this.tableDTOList = tableDTOList;
    }

    public boolean addToList(ProgramTemplateTableDTO programTemplateTableDTO) {
        return tableDTOList.add(programTemplateTableDTO);
    }

    public ProgramTemplateTableDTO removeFromList(int index) {
        return tableDTOList.remove(index);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramTemplateDto that = (ProgramTemplateDto) o;
        return getProgramName().equals(that.getProgramName()) &&
                getGoalDTO().equals(that.getGoalDTO()) &&
                getNumberOfDays().equals(that.getNumberOfDays()) &&
                isBaseProgram.equals(that.isBaseProgram) &&
                getCoachDTO().equals(that.getCoachDTO()) &&
                getTableDTOList().equals(that.getTableDTOList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProgramName(), getGoalDTO(), getNumberOfDays(), isBaseProgram,
                getCoachDTO(), getTableDTOList());
    }
}
