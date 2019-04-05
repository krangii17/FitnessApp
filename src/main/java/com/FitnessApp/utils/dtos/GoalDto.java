package com.FitnessApp.utils.dtos;

import java.util.Objects;

public class GoalDto {
    private Long id;
    private String goalTitle;

    public GoalDto() {
    }

    public GoalDto(Long id, String goalTitle) {
        this.id = id;
        this.goalTitle = goalTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoalTitle() {
        return goalTitle;
    }

    public void setGoalTitle(String goalTitle) {
        this.goalTitle = goalTitle;
    }

    @Override
    public String toString() {
        return "GoalDto{" +
                "id=" + id +
                ", goalTitle='" + goalTitle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoalDto)) return false;
        GoalDto goalDto = (GoalDto) o;
        return getId().equals(goalDto.getId()) &&
                getGoalTitle().equals(goalDto.getGoalTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGoalTitle());
    }
}
