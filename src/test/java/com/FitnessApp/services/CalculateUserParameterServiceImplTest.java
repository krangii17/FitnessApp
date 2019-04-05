package com.FitnessApp.services;

import com.FitnessApp.exceptions.IllegalWeightException;
import com.FitnessApp.exceptions.ImpossibleResultException;
import com.FitnessApp.services.impl.CalculateUserParameterServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculateUserParameterServiceImplTest {

    private CalculateUserParameterService service;

    @Before
    public void setUp() throws Exception {
        service = new CalculateUserParameterServiceImpl();
    }

    @Test
    public void getAmountsByMaximumAndCurrentWeight() throws ImpossibleResultException, IllegalWeightException {
        //GIVEN
        int expectedAmount = 3;
        //WHEN
        int actualAmount = service.getAmountsByMaximumAndCurrentWeight(100, 78);
        //THEN
        assertEquals(expectedAmount, actualAmount);
    }

    @Test(expected = ImpossibleResultException.class)
    public void getIncorrectAmountsByMaximumAndCurrentWeight() throws ImpossibleResultException, IllegalWeightException {
        service.getAmountsByMaximumAndCurrentWeight(100, 50);
    }


    @Test
    public void getUserMaximumDeadLiftByWeightAndAmount() {
        //GIVEN
        double actualMax = 97.07;
        //WHEN
        double expectedMax = service.getUserMaximumDeadLiftByWeightAndAmount(70, 7);
        //THEN
        assertEquals(actualMax, expectedMax, 0.1);
    }

    @Test
    public void getUserMaximumSquatByWeightAndAmount() {
        //GIVEN
        double actualMax = 92.05;
        //WHEN
        double expectedMax = service.getUserMaximumSquatByWeightAndAmount(70, 7);
        //THEN
        assertEquals(actualMax, expectedMax, 0.1);
    }

    @Test
    public void getUserMaximumBenchPressByWeightAndAmount() {
        //GIVEN
        double expectedMax = 85.4;
        //WHEN
        double actualMax = service.getUserMaximumBenchPressByWeightAndAmount(70, 7);
        //THEN
        assertEquals(expectedMax, actualMax, 0.1);
    }
}
