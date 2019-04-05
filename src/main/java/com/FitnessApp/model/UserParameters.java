package com.FitnessApp.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_parameters")
public class UserParameters {
    @Id
    @Column(name = "user_parameters_id", unique = true, nullable = false)
    private Long ID;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @NotNull
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Column(name = "weight")
    @NotNull
    private Integer weight;

    @Column(name = "height")
    @NotNull
    private Integer height;

    @Column(name = "train_day_quantity")
    @NotNull
    private Integer trainDayQuantity;

    @Column(name = "telephone")
    @Digits(fraction = 0, integer = 10)
    @NotEmpty
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "schedule_id")
    private Schedule schedule;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_parameters_restrictions",
            joinColumns = @JoinColumn(name = "user_parameter_ID"),
            inverseJoinColumns = @JoinColumn(name = "restriction_ID")
    )
    private Set<Restriction> restrictions;

    public UserParameters() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getTrainDayQuantity() {
        return trainDayQuantity;
    }

    public void setTrainDayQuantity(Integer trainDayQuantity) {
        this.trainDayQuantity = trainDayQuantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Set<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserParameters that = (UserParameters) o;
        return Objects.equals(ID, that.ID) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(birthDate, that.birthDate) &&
                gender == that.gender &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(height, that.height) &&
                Objects.equals(trainDayQuantity, that.trainDayQuantity) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(schedule, that.schedule) &&
                Objects.equals(restrictions, that.restrictions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, firstName, lastName, birthDate, gender, weight, height, trainDayQuantity, phone, schedule, restrictions);
    }

    @Override
    public String toString() {
        return "UserParameters{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", weight=" + weight +
                ", height=" + height +
                ", trainDayQuantity=" + trainDayQuantity +
                ", phone='" + phone + '\'' +
                ", schedule=" + schedule +
                ", restrictions=" + restrictions +
                '}';
    }
}
