package com.FitnessApp.utils.converters;

import com.FitnessApp.model.User;
import com.FitnessApp.model.UserProgram;
import com.FitnessApp.utils.dtos.UserProgramDTO;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class UserProgramConverterTest {

    private UserProgram userProgram;
    private UserProgramDTO userProgramDTO;
    private UserProgramConverter converter;
    private User user;

    @Before
    public void setUp() throws ParseException {
        user = new User();
        converter = new UserProgramConverter(new ModelMapper());
        userProgram = new UserProgram(user, "Base",
                new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-15"), false);
        userProgram.setId(1L);

        userProgramDTO = new UserProgramDTO(1L, "Base",
                new SimpleDateFormat("yyyy-MM-dd").parse("2019-11-15"), false);
    }

    @Test
    public void convertToDto_Test() {
        assertEquals(userProgramDTO,
                converter.convertToDto(userProgram));
    }

    @Test
    public void convertToEntity_Test() {
        UserProgram expected = converter.convertToEntity(userProgramDTO);
        expected.setUser(user);
        assertEquals(userProgram, expected);
    }
}
