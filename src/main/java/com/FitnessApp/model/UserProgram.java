package com.FitnessApp.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user_program")
public class UserProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(name = "program_name")
    private String programName;

    @NotNull
    @Type(type = "date")
    @Temporal(TemporalType.DATE)
    @Column(name = "begin_date")
    private Date beginDate;

    @NotNull
    @Column(name = "program_over")
    private boolean programOver;

    public UserProgram() {
    }

    public UserProgram(User user, @NotNull String programName, @NotNull Date beginDate,
                       @NotNull boolean programOver) {
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
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
        UserProgram that = (UserProgram) o;
        return isProgramOver() == that.isProgramOver() &&
                getId().equals(that.getId()) &&
                getUser().equals(that.getUser()) &&
                getProgramName().equals(that.getProgramName()) &&
                getBeginDate().equals(that.getBeginDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(),
                getProgramName(), getBeginDate(), isProgramOver());
    }
}
