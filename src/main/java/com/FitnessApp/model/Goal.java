package com.FitnessApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "goal")
public class Goal {
    @Id
    @Column(name = "goal_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "goal_title")
    private String goalTitle;


    public Goal() {
    }

    public Goal(String goalTitle) {
        this.goalTitle = goalTitle;
    }

    public Goal(Long id, String goalTitle) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goal)) return false;
        Goal goal = (Goal) o;
        return Objects.equals(getId(), goal.getId()) &&
                Objects.equals(getGoalTitle(), goal.getGoalTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGoalTitle());
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", goalTitle='" + goalTitle + '\'' +
                '}';
    }
}
