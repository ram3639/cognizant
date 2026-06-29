package com.pattern.singleton;

public class SingletonTest {
    public static void main(String[] args) {
        System.out.println("=== Testing Singleton Pattern ===");

        // Get two references to the Logger instance
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        // Perform some logging
        logger1.log("Initial logging system setup.");
        logger2.log("Checking another log entry.");

        // Print hash codes to see if they are identical
        System.out.println("Logger 1 reference hash code: " + System.identityHashCode(logger1));
        System.out.println("Logger 2 reference hash code: " + System.identityHashCode(logger2));

        // Verify that both references point to the same instance
        boolean areEqual = (logger1 == logger2);
        System.out.println("Are both logger instances identical? " + areEqual);

        if (areEqual) {
            System.out.println("\nSUCCESS: The Logger class correctly implements the Singleton Pattern!");
        } else {
            System.out.println("\nFAILURE: The Logger class violated the Singleton Pattern!");
        }
    }
}
