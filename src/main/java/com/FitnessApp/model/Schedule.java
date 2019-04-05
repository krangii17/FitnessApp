package com.FitnessApp.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "schedule_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotEmpty
    @Column(name = "purpose")
    private String purpose;

    @OneToMany(mappedBy = "schedule",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Training> trainings = new ArrayList<>();

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return ID.equals(schedule.ID) &&
                purpose.equals(schedule.purpose) &&
                trainings.equals(schedule.trainings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, purpose, trainings);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "ID=" + ID +
                ", purpose='" + purpose + '\'' +
                ", trainings=" + trainings +
                '}';
    }
}
