package forecasting;

import java.util.HashMap;
import java.util.Map;

public class FinancialForecasting {

    /**
     * Naive recursive approach to calculate future value by applying a series of growth rates.
     * While this runs in O(N) time, it uses O(N) auxiliary space on the call stack.
     * For a large number of periods, it can cause a StackOverflowError.
     *
     * @param currentValue the value at the current step
     * @param growthRates  the array of growth rates
     * @param index        the current rate index to apply
     * @return the predicted future value
     */
    public static double calculateFutureValueRecursive(double currentValue, double[] growthRates, int index) {
        // Base Case: no more growth rates to apply
        if (index >= growthRates.length) {
            return currentValue;
        }
        // Recursive Step: apply growth rate at current index and recurse for the next index
        return calculateFutureValueRecursive(currentValue * (1 + growthRates[index]), growthRates, index + 1);
    }

    /**
     * Optimized iterative approach to calculate future value.
     * This avoids recursion entirely, running in O(N) time and O(1) auxiliary space.
     *
     * @param initialValue the starting value
     * @param growthRates  the array of growth rates
     * @return the predicted future value
     */
    public static double calculateFutureValueIterative(double initialValue, double[] growthRates) {
        double value = initialValue;
        for (double rate : growthRates) {
            value *= (1 + rate);
        }
        return value;
    }

    /**
     * Naive recursive model representing a Fibonacci-like financial growth model,
     * where the growth rate of year N depends on the growth rates of year N-1 and N-2:
     *   GrowthRate(n) = 0.5 * GrowthRate(n-1) + 0.3 * GrowthRate(n-2)
     *
     * Computing this naively takes exponential time O(2^N) due to redundant calculations.
     *
     * @param n the year index
     * @param baseRate1 growth rate of year 1
     * @param baseRate2 growth rate of year 2
     * @return the growth rate for year n
     */
    public static double predictGrowthRateNaive(int n, double baseRate1, double baseRate2) {
        if (n == 1) return baseRate1;
        if (n == 2) return baseRate2;
        return 0.5 * predictGrowthRateNaive(n - 1, baseRate1, baseRate2)
             + 0.3 * predictGrowthRateNaive(n - 2, baseRate1, baseRate2);
    }

    /**
     * Optimized recursive model using memoization (dynamic programming) to calculate
     * the recursive growth rates.
     * This reduces time complexity from O(2^N) to O(N).
     *
     * @param n the year index
     * @param baseRate1 growth rate of year 1
     * @param baseRate2 growth rate of year 2
     * @param memo a map to store previously computed growth rates
     * @return the growth rate for year n
     */
    public static double predictGrowthRateMemoized(int n, double baseRate1, double baseRate2, Map<Integer, Double> memo) {
        if (n == 1) return baseRate1;
        if (n == 2) return baseRate2;
        
        if (memo.containsKey(n)) {
            return memo.get(n);
        }
        
        double result = 0.5 * predictGrowthRateMemoized(n - 1, baseRate1, baseRate2, memo)
                      + 0.3 * predictGrowthRateMemoized(n - 2, baseRate1, baseRate2, memo);
        
        memo.put(n, result);
        return result;
    }
}
