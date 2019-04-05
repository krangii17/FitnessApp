package com.FitnessApp.services;

import com.FitnessApp.exceptions.IllegalWeightException;
import com.FitnessApp.exceptions.ImpossibleResultException;

public interface CalculateUserParameterService {

    double getUserMaximumDeadLiftByWeightAndAmount(double weight, int amount);

    double getUserMaximumSquatByWeightAndAmount(double weight, int amount);

    double getUserMaximumBenchPressByWeightAndAmount(double weight, int amount);

    int getAmountsByMaximumAndCurrentWeight(double maxWeight, double currentWeight) throws ImpossibleResultException, IllegalWeightException;
}
