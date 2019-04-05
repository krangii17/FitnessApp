package com.FitnessApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_start_parameters")
public class UserStartParam {
    @Id
    @Column(name = "user_start_param_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "init_weight")
    private Integer initialWeight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_template_id")
    private ProgramTemplate programTemplate;

    public UserStartParam() { }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Integer getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(Integer initialWeight) {
        this.initialWeight = initialWeight;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public ProgramTemplate getProgramTemplate() {
        return programTemplate;
    }

    public void setProgramTemplate(ProgramTemplate programTemplate) {
        this.programTemplate = programTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStartParam)) return false;
        UserStartParam that = (UserStartParam) o;
        return initialWeight == that.initialWeight &&
                ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, initialWeight);
    }

    @Override
    public String toString() {
        return "UserStartParam{" +
                "ID=" + ID +
                ", initialWeight=" + initialWeight +
                '}';
    }
}
