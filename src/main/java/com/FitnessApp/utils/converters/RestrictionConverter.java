package com.FitnessApp.utils.converters;

import com.FitnessApp.model.Restriction;
import com.FitnessApp.utils.dtos.RestrictionDTO;
import org.springframework.stereotype.Component;

@Component
public class RestrictionConverter {

    public Restriction convertToEntity(RestrictionDTO restrictionDTO) {
        Restriction restriction = new Restriction();
        restriction.setRestID(restrictionDTO.getId());
        restriction.setName(restrictionDTO.getName());
        return restriction;
    }

    public RestrictionDTO convertToDto(Restriction restriction) {
        return new RestrictionDTO(
                restriction.getRestID(), restriction.getName());
    }
}
