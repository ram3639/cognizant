package com.pattern.singleton;

public class Logger {
    // Volatile instance to ensure thread-safety during double-checked locking
    private static volatile Logger instance;

    // Private constructor to prevent direct instantiation
    private Logger() {
        // Prevent instantiation via reflection
        if (instance != null) {
            throw new IllegalStateException("Instance already created");
        }
    }

    // Public static method to get the single instance of the Logger class
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // Method to simulate logging
    public void log(String message) {
        System.out.println("[LOG]: " + message);
    }
}
