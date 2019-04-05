package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAOImpl;
import com.FitnessApp.exceptions.ObjectNotFoundException;
import com.FitnessApp.model.MusclesGroup;
import com.FitnessApp.services.impl.MusclesGroupService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class MusclesGroupServiceTest {

    @Mock
    GenericDAOImpl dao;

    @InjectMocks
    MusclesGroupService service;

    private List<MusclesGroup> musclesGroupList;

    private Long index;

    private MusclesGroup getMusclesGroupObject(Long id, String groupName) {
        MusclesGroup musclesGroup = new MusclesGroup();
        musclesGroup.setID(id);
        musclesGroup.setGroupName(groupName);
        return musclesGroup;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        index = 0L;

        musclesGroupList = new ArrayList<>();
        musclesGroupList.add(getMusclesGroupObject(1L, "arm"));
        musclesGroupList.add(getMusclesGroupObject(2L, "leg"));
        musclesGroupList.add(getMusclesGroupObject(2L, "back"));
    }

    @Test
    public void getAll_Test() {
        List<MusclesGroup> expected = musclesGroupList;
        when(dao.getAll(MusclesGroup.class)).thenReturn(Optional.ofNullable(musclesGroupList));

        Optional<List<MusclesGroup>> actual = service.getAll();

        assertEquals(expected, actual.get());
    }

    @Test
    public void getByParameter_Test() throws ObjectNotFoundException {

        MusclesGroup expected = musclesGroupList.get(index.intValue());
        when(dao.getByParameter(MusclesGroup.class, "groupName",
                musclesGroupList.get(index.intValue()).getGroupName()))
                .thenReturn(Optional.ofNullable(musclesGroupList));

        MusclesGroup actual = service.getByParameter("groupName",
                musclesGroupList.get(index.intValue()).getGroupName()).get(0);

        assertEquals(expected, actual);

    }

    @Test(expected = ObjectNotFoundException.class)
    public void getByParameter_Not_Found_Test() {

        when(dao.getByParameter(MusclesGroup.class, "groupName",
                musclesGroupList.get(index.intValue()).getGroupName()))
                .thenReturn(Optional.ofNullable(null));

        MusclesGroup actual = service.getByParameter("groupName",
                musclesGroupList.get(index.intValue()).getGroupName()).get(0);
    }


    @Test
    public void getByID_Test() {

        MusclesGroup expected = musclesGroupList.get(index.intValue());
        when(dao.getByID(MusclesGroup.class, musclesGroupList.get(index.intValue()).getID()))
                .thenReturn(Optional.ofNullable(musclesGroupList.get(index.intValue())));

        MusclesGroup actual = service.getByID(musclesGroupList.get(index.intValue()).getID());

        assertEquals(expected, actual);
    }


    @Test(expected = ObjectNotFoundException.class)
    public void getByID_Not_Found_Test() {

        when(dao.getByID(MusclesGroup.class, musclesGroupList.get(index.intValue()).getID()))
                .thenReturn(Optional.ofNullable(null));

        service.getByID(musclesGroupList.get(index.intValue()).getID());
    }

    @Test
    public void save_Test() {

        MusclesGroup savedObj = musclesGroupList.get(index.intValue());

        doNothing().when(dao).save(savedObj);

        boolean actual = service.save(savedObj);
        assertTrue(actual);

        savedObj.setGroupName("");
        actual = service.save(savedObj);
        assertFalse(actual);
    }

    @Test
    public void update_Test() {

        MusclesGroup updatedObj = musclesGroupList.get(index.intValue());

        when(dao.getByID(MusclesGroup.class, index))
                .thenReturn(Optional.ofNullable(updatedObj));

        when(dao.update(updatedObj)).thenReturn(updatedObj);

        boolean actual = service.update(index, updatedObj);
        assertTrue(actual);

        updatedObj.setGroupName("");
        actual = service.update(index, updatedObj);
        assertFalse(actual);

    }

    @Test(expected = ObjectNotFoundException.class)
    public void update_Not_Found_Test() {

        when(dao.getByID(MusclesGroup.class, index))
                .thenReturn(Optional.ofNullable(null));

        service.update(index, musclesGroupList.get(index.intValue()));
    }


    @Test
    public void delete_Test() {

        doNothing().when(dao).delete(musclesGroupList.get(index.intValue()));

        service.delete(musclesGroupList.get(index.intValue()));

    }


    @Test(expected = ObjectNotFoundException.class)
    public void delete_Not_Found_Test() {
        MusclesGroup musclesGroup = new MusclesGroup();
        musclesGroup.setGroupName("Arm");

        service.delete(musclesGroup);
    }

    @Test
    public void deleteById_Test() {

        when(dao.getByID(MusclesGroup.class, index))
                .thenReturn(Optional.ofNullable(musclesGroupList.get(index.intValue())));

        doNothing().when(dao).deleteById(MusclesGroup.class, index);

        service.deleteById(index);

    }

    @Test(expected = ObjectNotFoundException.class)
    public void deleteById_Not_Found_Test() {

        when(dao.getByID(MusclesGroup.class, index))
                .thenReturn(Optional.ofNullable(null));

        service.deleteById(index);

    }

}
