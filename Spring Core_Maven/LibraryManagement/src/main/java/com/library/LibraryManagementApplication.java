package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;

public class LibraryManagementApplication {
    public static void main(String[] args) {
        System.out.println("=== Initializing Spring Application Context ===");
        
        // Load the application configuration from the XML configuration file
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        System.out.println("=== Spring Context Loaded Successfully ===");

        // Retrieve the BookService bean from the application context
        BookService bookService = (BookService) context.getBean("bookService");

        // Test the configuration and dependency injection
        System.out.println("\n=== Testing Library Management Application ===");
        bookService.addBook("Spring Framework Reference");
        bookService.addBook("Clean Code");
        System.out.println("===============================================");
        
        // Close the application context to release resources
        context.close();
    }
}
