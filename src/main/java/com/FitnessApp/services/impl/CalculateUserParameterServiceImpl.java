package com.FitnessApp.services.impl;

import com.FitnessApp.exceptions.IllegalWeightException;
import com.FitnessApp.exceptions.ImpossibleResultException;
import com.FitnessApp.services.CalculateUserParameterService;
import org.springframework.stereotype.Service;

@Service
public class CalculateUserParameterServiceImpl implements CalculateUserParameterService {

    private static final double SQUAT_FACTOR = 0.045;
    private static final double BENCH_PRESS_FACTOR = 0.035;
    private static final double DEADLIFT_FACTOR = 0.065;
    private static final int MAX_AMOUNT = 10;
    private static final double PHYSICAL_FACTOR = 5;
    private static final double MAX_WEIGHT_FACTOR = 0.75;


    @Override
    public int getAmountsByMaximumAndCurrentWeight(double maxWeight, double currentWeight) throws ImpossibleResultException, IllegalWeightException {
        if ((currentWeight / maxWeight) > MAX_WEIGHT_FACTOR) {
            return getMyAmountByWeight(maxWeight, currentWeight);
        } else {
            throw new ImpossibleResultException("It is not impossible to reach this by your current results");
        }
    }

    private int getMyAmountByWeight(double max, double current) throws IllegalWeightException {
        for (int i = 0; i < MAX_AMOUNT; i++) {
            double amountByCurrent = current + ((current * Math.pow(PHYSICAL_FACTOR, i)) / 100);
            if (amountByCurrent >= max) {
                return i;
            }
        }
        throw new IllegalWeightException("Your weight is incorrect");
    }

    private double getMyDeadLiftFactorByAmount(int amount) {
        if (amount < 2) {
            return (1 + (amount * DEADLIFT_FACTOR * 1));
        } else if (amount < 4) {
            return (1 + (amount * DEADLIFT_FACTOR * 0.9));
        } else if (amount < 8) {
            return (1 + (amount * DEADLIFT_FACTOR * 0.85));
        } else {
            return (1 + (MAX_AMOUNT * DEADLIFT_FACTOR * 0.8));
        }
    }

    @Override
    public double getUserMaximumDeadLiftByWeightAndAmount(double weight, int amount) {
        return getMyDeadLiftFactorByAmount(amount) * weight;
    }

    private double getMySquatFactorByAmount(int amount) {
        if (amount < MAX_AMOUNT) {
            return (1 + (amount * SQUAT_FACTOR));
        } else {
            return (1 + (MAX_AMOUNT * SQUAT_FACTOR));
        }
    }

    @Override
    public double getUserMaximumSquatByWeightAndAmount(double weight, int amount) {
        return getMySquatFactorByAmount(amount) * weight;
    }

    private double getMyBenchPressFactorByAmount(int amount) {
        if (amount < 3) {
            return (1 + (amount * BENCH_PRESS_FACTOR * 1.1));
        } else if (amount < 6) {
            return (1 + (amount * BENCH_PRESS_FACTOR * 1));
        } else if (amount < MAX_AMOUNT) {
            return (1 + (amount * BENCH_PRESS_FACTOR * 0.9));
        } else {
            return (1 + (MAX_AMOUNT * BENCH_PRESS_FACTOR * 0.9));
        }
    }

    @Override
    public double getUserMaximumBenchPressByWeightAndAmount(double weight, int amount) {
        return getMyBenchPressFactorByAmount(amount) * weight;
    }
}
