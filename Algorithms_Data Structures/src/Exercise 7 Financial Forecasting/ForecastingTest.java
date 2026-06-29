package forecasting;

import java.util.HashMap;
import java.util.Map;

public class ForecastingTest {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("        EXERCISE 7: FINANCIAL FORECASTING         ");
        System.out.println("==================================================");

        // 1. Future Value Calculation Correctness Verification
        System.out.println("\n--- Part 1: Future Value Forecast ---");
        double initialValue = 10000.0; // Starting with $10,000
        double[] growthRates = {0.05, 0.03, -0.01, 0.04, 0.06}; // 5 years of growth rates
        
        System.out.println("Initial Value: $" + initialValue);
        System.out.print("Growth Rates: ");
        for (double rate : growthRates) {
            System.out.print((rate * 100) + "%  ");
        }
        System.out.println();

        double recursiveResult = FinancialForecasting.calculateFutureValueRecursive(initialValue, growthRates, 0);
        double iterativeResult = FinancialForecasting.calculateFutureValueIterative(initialValue, growthRates);

        System.out.printf("Recursive Future Value (Year 5): $%.2f\n", recursiveResult);
        System.out.printf("Iterative Future Value (Year 5): $%.2f\n", iterativeResult);

        // 2. Redundant Computation Performance Comparison (Exponential vs Linear)
        System.out.println("\n--- Part 2: Recursion Performance and Optimization ---");
        int targetYear = 35;
        double baseRate1 = 0.04;
        double baseRate2 = 0.05;

        System.out.println("Predicting Growth Rate for Year " + targetYear + " using Fibonacci-like model...");

        // Measure Naive Recursive Approach
        System.out.println("Running Naive Recursive method (O(2^N))...");
        long startTime = System.nanoTime();
        double naiveRate = FinancialForecasting.predictGrowthRateNaive(targetYear, baseRate1, baseRate2);
        long naiveDuration = System.nanoTime() - startTime;

        // Measure Memoized Recursive Approach
        System.out.println("Running Memoized Recursive method (O(N))...");
        Map<Integer, Double> memo = new HashMap<>();
        startTime = System.nanoTime();
        double memoizedRate = FinancialForecasting.predictGrowthRateMemoized(targetYear, baseRate1, baseRate2, memo);
        long memoizedDuration = System.nanoTime() - startTime;

        // Display performance details
        System.out.printf("\nResults for Year %d:\n", targetYear);
        System.out.printf("  %-25s : %.6f%%\n", "Naive Predicted Rate", naiveRate * 100);
        System.out.printf("  %-25s : %.6f%%\n", "Memoized Predicted Rate", memoizedRate * 100);
        
        System.out.println("\nPerformance Summary:");
        System.out.printf("  %-25s : %.3f ms\n", "Naive Recursive (O(2^N))", (naiveDuration / 1_000_000.0));
        System.out.printf("  %-25s : %.3f ms\n", "Memoized Recursive (O(N))", (memoizedDuration / 1_000_000.0));
        System.out.printf("  Optimization Factor       : %.1fx faster\n", (double) naiveDuration / memoizedDuration);
        System.out.println("==================================================");
    }
}
