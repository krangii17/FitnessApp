package com.FitnessApp.utils.converters;

import com.FitnessApp.model.MusclesGroup;
import com.FitnessApp.utils.dtos.MusclesGroupDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MusclesGroupConverterTest {

    private MusclesGroup musclesGroup;
    private MusclesGroupDTO musclesGroupDTO;
    private MusclesGroupConverter converter;


    @Before
    public void setUp() {
        converter = new MusclesGroupConverter();

        musclesGroup = new MusclesGroup();
        musclesGroup.setID(1L);
        musclesGroup.setGroupName("Arm");

        musclesGroupDTO = new MusclesGroupDTO(1L, "Arm");
    }

    @Test
    public void asMusclesGroup_Test() {
        MusclesGroup expected = musclesGroup;
        MusclesGroup actual = converter.asMusclesGroup(musclesGroupDTO);
        assertEquals(expected.getID(), actual.getID());
        assertEquals(expected.getGroupName(), actual.getGroupName());

    }

    @Test
    public void asMusclesGroupDTO_Test() {
        MusclesGroupDTO expected = musclesGroupDTO;
        MusclesGroupDTO actual = converter.asMusclesGroupDTO(musclesGroup);
        assertEquals(expected.getID(), actual.getID());
        assertEquals(expected.getGroupName(), actual.getGroupName());
    }
}
