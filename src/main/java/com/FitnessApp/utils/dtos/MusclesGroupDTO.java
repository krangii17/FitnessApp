package com.FitnessApp.utils.dtos;

import java.util.Objects;

public class MusclesGroupDTO {
    private Long ID;
    private String groupName;

    public MusclesGroupDTO() {
    }

    public MusclesGroupDTO(Long ID, String groupName) {
        this.ID = ID;
        this.groupName = groupName;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusclesGroupDTO that = (MusclesGroupDTO) o;
        return getID().equals(that.getID()) &&
                getGroupName().equals(that.getGroupName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getGroupName());
    }
}
