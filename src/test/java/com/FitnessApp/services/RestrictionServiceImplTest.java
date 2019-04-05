package com.FitnessApp.services;

import com.FitnessApp.dao.GenericDAO;
import com.FitnessApp.exceptions.RestrictionNotFoundException;
import com.FitnessApp.model.Restriction;
import com.FitnessApp.model.State;
import com.FitnessApp.services.impl.RestrictionServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class RestrictionServiceImplTest {

    int firstElement;
    long idOfCheckingElement;
    private Restriction restriction;
    private List<Restriction> restrictions;
    @Mock
    private RestrictionService restrictionService;
    @Mock
    private GenericDAO dao;

    @Before
    public void setUP() {
        MockitoAnnotations.initMocks(this);
        restrictionService = new RestrictionServiceImpl(dao);

        restriction = new Restriction();
        restriction.setRestID(1L);
        restriction.setName("regularRestriction");
        restriction.setState(State.ACTIVE);
        restriction.setEvaluation(5);

        restrictions = new ArrayList<>();
        restrictions.add(restriction);
        firstElement = 0;
        idOfCheckingElement = 1L;
    }

    @After
    public void tearDown() {
        restrictions.clear();
    }

    @Test
    public void changeRestStatus() throws RestrictionNotFoundException {
        Restriction updatedRestriciton = restrictions.get(firstElement);
        updatedRestriciton.setState(State.DISACTIVE);

        when(dao.getByID(Restriction.class, idOfCheckingElement))
                .thenReturn(Optional.ofNullable(updatedRestriciton));

        when(dao.update(updatedRestriciton)).thenReturn(updatedRestriciton);

        boolean actual = restrictionService.changeEvaluation(idOfCheckingElement, updatedRestriciton);
        assertTrue(actual);
    }

    @Test
    public void getAllRestrictions() {
        List<Restriction> expected = restrictions;
        when(dao.getAll(Restriction.class)).thenReturn(Optional.ofNullable(restrictions));

        Optional<List<Restriction>> actual = restrictionService.getAllRestrictions();

        assertEquals(expected, actual.get());
    }

    @Test
    public void changeEvaluation() throws RestrictionNotFoundException {
        Restriction updatedRestriciton = restrictions.get(firstElement);
        updatedRestriciton.setEvaluation(7);

        when(dao.getByID(Restriction.class, idOfCheckingElement))
                .thenReturn(Optional.ofNullable(updatedRestriciton));

        when(dao.update(updatedRestriciton)).thenReturn(updatedRestriciton);

        boolean actual = restrictionService.changeEvaluation(idOfCheckingElement, updatedRestriciton);
        assertTrue(actual);
    }

    @Test(expected = RestrictionNotFoundException.class)
    public void changeEvaluationException() throws RestrictionNotFoundException {
        Restriction updatedRestriciton = restrictions.get(firstElement);
        updatedRestriciton.setEvaluation(7);

        when(dao.getByID(Restriction.class, idOfCheckingElement))
                .thenReturn(Optional.empty());

        when(dao.update(updatedRestriciton)).thenReturn(updatedRestriciton);
        restrictionService.changeEvaluation(idOfCheckingElement, updatedRestriciton);
    }

    @Test
    public void deleteRestrictionByID() throws RestrictionNotFoundException {
        when(dao.getByID(Restriction.class, idOfCheckingElement))
                .thenReturn(Optional.ofNullable(restrictions.get(firstElement)));

        doNothing().when(dao).deleteById(Restriction.class, idOfCheckingElement);

        assertTrue(restrictionService.deleteRestrictionByID(idOfCheckingElement));
    }

    @Test(expected = RestrictionNotFoundException.class)
    public void getExceptionWhenDeleting() throws RestrictionNotFoundException {
        when(dao.getByID(Restriction.class, idOfCheckingElement))
                .thenReturn(Optional.empty());

        doNothing().when(dao).deleteById(Restriction.class, idOfCheckingElement);
        restrictionService.deleteRestrictionByID(idOfCheckingElement);
    }
}
