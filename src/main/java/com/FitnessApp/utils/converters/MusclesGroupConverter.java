package com.FitnessApp.utils.converters;

import com.FitnessApp.model.MusclesGroup;
import com.FitnessApp.utils.dtos.MusclesGroupDTO;
import org.springframework.stereotype.Component;

@Component
public class MusclesGroupConverter {
    public MusclesGroup asMusclesGroup(MusclesGroupDTO musclesGroupDto) {
        MusclesGroup musclesGroup = new MusclesGroup();
        musclesGroup.setID(musclesGroupDto.getID());
        musclesGroup.setGroupName(musclesGroupDto.getGroupName());
        return musclesGroup;
    }

    public MusclesGroupDTO asMusclesGroupDTO(MusclesGroup musclesGroup) {
        return new MusclesGroupDTO(
                musclesGroup.getID(), musclesGroup.getGroupName());
    }

}
