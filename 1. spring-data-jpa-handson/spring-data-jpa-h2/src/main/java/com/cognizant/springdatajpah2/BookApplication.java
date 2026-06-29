package com.cognizant.springdatajpah2;

import com.cognizant.springdatajpah2.model.Book;
import com.cognizant.springdatajpah2.repository.BookRepository;
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
        System.out.println("=== Starting Spring Data JPA H2 Demo ===");

        // 1. Add records
        System.out.println("\n--- Saving Books via Spring Data JPA ---");
        Book book1 = bookRepository.save(new Book("Spring Boot in Action (Spring Data H2)", new BigDecimal("49.99"), LocalDate.of(2020, 5, 20)));
        Book book2 = bookRepository.save(new Book("Java Concurrency in Practice (Spring Data H2)", new BigDecimal("59.90"), LocalDate.of(2006, 5, 9)));
        Book book3 = bookRepository.save(new Book("Effective Java (Spring Data H2)", new BigDecimal("45.00"), LocalDate.of(2018, 1, 11)));

        System.out.println("Successfully saved Book [ID: " + book1.getId() + ", Title: '" + book1.getTitle() + "']");
        System.out.println("Successfully saved Book [ID: " + book2.getId() + ", Title: '" + book2.getTitle() + "']");
        System.out.println("Successfully saved Book [ID: " + book3.getId() + ", Title: '" + book3.getTitle() + "']");

        // 2. Query all records
        System.out.println("\n--- Querying All Books ---");
        List<Book> allBooks = bookRepository.findAll();
        for (Book book : allBooks) {
            System.out.println("Fetched: " + book);
        }

        // 3. Query using custom dynamic finder
        System.out.println("\n--- Querying Books containing 'Java' (Custom Dynamic Query) ---");
        List<Book> javaBooks = bookRepository.findByTitleContaining("Java");
        for (Book book : javaBooks) {
            System.out.println("Found: " + book);
        }

        System.out.println("\n=== Finished Spring Data JPA H2 Demo ===");
    }
}
