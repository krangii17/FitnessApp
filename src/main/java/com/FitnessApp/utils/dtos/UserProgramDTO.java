package com.FitnessApp.utils.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class UserProgramDTO {
    private Long id;
    private String programName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date beginDate;
    private boolean programOver;

    public UserProgramDTO() {
        programOver = false;
    }

    public UserProgramDTO(Long id, String programName,
                          Date beginDate, boolean programOver) {
        this.id = id;
        this.programName = programName;
        this.beginDate = beginDate;
        this.programOver = programOver;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public boolean isProgramOver() {
        return programOver;
    }

    public void setProgramOver(boolean programOver) {
        this.programOver = programOver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProgramDTO that = (UserProgramDTO) o;
        return isProgramOver() == that.isProgramOver() &&
                getId().equals(that.getId()) &&
                getProgramName().equals(that.getProgramName()) &&
                getBeginDate().equals(that.getBeginDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProgramName(), getBeginDate(), isProgramOver());
    }
}
