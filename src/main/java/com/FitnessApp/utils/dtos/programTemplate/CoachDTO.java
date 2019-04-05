package com.FitnessApp.utils.dtos.programTemplate;

import java.util.Objects;

public class CoachDTO {
    private Long coachId;
    private String coachName;

    public CoachDTO() {
    }

    public CoachDTO(Long coachId, String coachName) {
        this.coachId = coachId;
        this.coachName = coachName;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachDTO coachDTO = (CoachDTO) o;
        return getCoachId().equals(coachDTO.getCoachId()) &&
                getCoachName().equals(coachDTO.getCoachName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCoachId(), getCoachName());
    }
}
