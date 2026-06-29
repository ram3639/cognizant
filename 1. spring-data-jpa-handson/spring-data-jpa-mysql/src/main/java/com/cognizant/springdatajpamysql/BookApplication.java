package com.cognizant.springdatajpamysql;

import com.cognizant.springdatajpamysql.model.Book;
import com.cognizant.springdatajpamysql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BookApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Starting Spring Data JPA MySQL Demo ===");

        try {
            // 1. Add records
            System.out.println("\n--- Saving Books via Spring Data JPA (MySQL) ---");
            Book book1 = bookRepository.save(new Book("Spring Boot in Action (Spring Data MySQL)", new BigDecimal("49.99"), LocalDate.of(2020, 5, 20)));
            Book book2 = bookRepository.save(new Book("Java Concurrency in Practice (Spring Data MySQL)", new BigDecimal("59.90"), LocalDate.of(2006, 5, 9)));
            Book book3 = bookRepository.save(new Book("Effective Java (Spring Data MySQL)", new BigDecimal("45.00"), LocalDate.of(2018, 1, 11)));

            System.out.println("Successfully saved Book [ID: " + book1.getId() + ", Title: '" + book1.getTitle() + "']");
            System.out.println("Successfully saved Book [ID: " + book2.getId() + ", Title: '" + book2.getTitle() + "']");
            System.out.println("Successfully saved Book [ID: " + book3.getId() + ", Title: '" + book3.getTitle() + "']");

            // 2. Query all records
            System.out.println("\n--- Querying All Books from MySQL ---");
            List<Book> allBooks = bookRepository.findAll();
            for (Book book : allBooks) {
                System.out.println("Fetched from MySQL: " + book);
            }

            // 3. Query using custom dynamic finder
            System.out.println("\n--- Querying Books containing 'Java' (Custom Dynamic Query) ---");
            List<Book> javaBooks = bookRepository.findByTitleContaining("Java");
            for (Book book : javaBooks) {
                System.out.println("Found in MySQL: " + book);
            }
        } catch (Exception e) {
            System.err.println("\n[WARNING] Could not connect to MySQL database server.");
            System.err.println("Please make sure your MySQL service is running on localhost:3306 with schema 'testdb', user 'root', and password 'password',");
            System.err.println("or update 'spring-data-jpa-mysql/src/main/resources/application.properties' with correct details.");
            System.err.println("Error details: " + e.getMessage());
        }

        System.out.println("\n=== Finished Spring Data JPA MySQL Demo ===");
    }
}
