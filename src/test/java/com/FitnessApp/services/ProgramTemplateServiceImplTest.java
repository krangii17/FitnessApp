package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.dao.GenericDAOImpl;
import com.FitnessApp.exceptions.ProgramTemplateException;
import com.FitnessApp.model.ProgramTemplate;
import com.FitnessApp.services.impl.ProgramTemplateServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProgramTemplateServiceImplTest {

    @Mock
    private GenericDAO dao = mock(GenericDAOImpl.class);

    private ProgramTemplateService service;

    private List<ProgramTemplate> programTemplates;

    private ProgramTemplate programTemplate1;
    private ProgramTemplate programTemplate2;
    private ProgramTemplate programTemplate3;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ProgramTemplateServiceImpl(dao);

        programTemplate1 = new ProgramTemplate();
        programTemplate1.setNumOfTrain(1);
        programTemplate1.setProgramName("prog1");

        programTemplate2 = new ProgramTemplate();
        programTemplate2.setNumOfTrain(1);
        programTemplate2.setProgramName("prog2");

        programTemplates = new ArrayList<>();
        programTemplates.add(programTemplate1);
        programTemplates.add(programTemplate2);

    }

    @Test
    public void shouldReturnAllProgramTemplates() {
        List<ProgramTemplate> expected = programTemplates;
        when(dao.getAll(ProgramTemplate.class)).thenReturn(Optional.ofNullable(programTemplates));

        Optional<List<ProgramTemplate>> actual = service.getAll();

        assertEquals(expected, actual.get());
        assertEquals(expected.size(), 2);
    }

    @Test
    public void shouldReturnProgramTemplateById() {
        int index = 0;
        ProgramTemplate expected = programTemplates.get(index);
        when(dao.getByID(ProgramTemplate.class, programTemplates.get(index).getId()))
                .thenReturn(Optional.ofNullable(programTemplates.get(index)));

        ProgramTemplate actual = service.getById(programTemplates.get(index).getId());

        assertEquals(expected, actual);
    }

    @Test(expected = ProgramTemplateException.class)
    public void shouldThrowProgramTemplateExceptionCauseEnterInvalidId() {
        int index = 0;
        when(dao.getByID(ProgramTemplate.class, programTemplates.get(index).getId()))
                .thenReturn(Optional.empty());

        service.getById(programTemplates.get(index).getId());
    }

    @Test
    public void shouldAddNewProgramTemplate() {
        ProgramTemplate programTemplateToSave = new ProgramTemplate();

        programTemplateToSave.setProgramName("save");

        doNothing().when(dao).save(programTemplateToSave);

        assertTrue(service.addProgramTemplate(programTemplateToSave));
    }

    @Test
    public void shouldNotAddNewProgramTemplate() {
        ProgramTemplate programTemplateToSave = new ProgramTemplate();
        programTemplateToSave.setProgramName("");

        doNothing().when(dao).save(programTemplateToSave);

        assertFalse(service.addProgramTemplate(programTemplateToSave));
    }

    @Test
    public void shouldUpdateNameInProgramTemplate() {

        ProgramTemplate updatedObj = programTemplates.get(0);
        updatedObj.setProgramName("UPD");

        when(dao.getByID(ProgramTemplate.class, 0L))
                .thenReturn(Optional.ofNullable(updatedObj));

        when(dao.update(updatedObj)).thenReturn(updatedObj);

        boolean actual = service.updateProgramTemplate(0L, updatedObj);
        assertTrue(actual);
    }

    @Test
    public void shouldNotUpdateNameInProgramTemplate() {

        ProgramTemplate updatedObj = programTemplates.get(0);
        updatedObj.setProgramName("");

        when(dao.getByID(ProgramTemplate.class, 0L))
                .thenReturn(Optional.ofNullable(updatedObj));

        when(dao.update(updatedObj)).thenReturn(updatedObj);

        boolean actual = service.updateProgramTemplate(0L, updatedObj);
        assertFalse(actual);
    }

    @Test
    public void shouldDeleteProgramTemplate() {

        when(dao.getByID(ProgramTemplate.class, 0L))
                .thenReturn(Optional.ofNullable(programTemplates.get(0)));

        doNothing().when(dao).deleteById(ProgramTemplate.class, 0L);

        assertTrue(service.deleteProgramTemplateById(0L));

    }

    @Test
    public void getByName_Test() {
        String programName = "programName";
        List<ProgramTemplate> expected = programTemplates;
        when(dao.getByParameter(ProgramTemplate.class, programName, programName))
                .thenReturn(Optional.ofNullable(programTemplates));

        List<ProgramTemplate> actual = service.getByName(programName);

        assertEquals(expected, actual);
    }

}
