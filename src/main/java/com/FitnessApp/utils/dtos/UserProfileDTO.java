package com.FitnessApp.utils.dtos;

import com.FitnessApp.model.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class UserProfileDTO {


    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String telephone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
    private Gender gender;
    private Integer weight;
    private Integer height;
    private int trainDayQuantity;

    public UserProfileDTO() {
    }

    public UserProfileDTO(long id,
                          String firstName,
                          String lastName,
                          String username,
                          String email,
                          String telephone,
                          Date birthDate,
                          Gender gender,
                          Integer weight,
                          Integer height,
                          int trainDayQuantity) {
        this.id = id;
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.telephone = telephone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.trainDayQuantity = trainDayQuantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public int getTrainDayQuantity() {
        return trainDayQuantity;
    }

    public void setTrainDayQuantity(int trainDayQuantity) {
        this.trainDayQuantity = trainDayQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileDTO that = (UserProfileDTO) o;
        return getId() == that.getId() &&
                getTrainDayQuantity() == that.getTrainDayQuantity() &&
                getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getUsername().equals(that.getUsername()) &&
                getEmail().equals(that.getEmail()) &&
                getTelephone().equals(that.getTelephone()) &&
                getBirthDate().equals(that.getBirthDate()) &&
                getGender() == that.getGender() &&
                getWeight().equals(that.getWeight()) &&
                getHeight().equals(that.getHeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getUsername(), getEmail(), getTelephone(), getBirthDate(), getGender(), getWeight(), getHeight(), getTrainDayQuantity());
    }
}
