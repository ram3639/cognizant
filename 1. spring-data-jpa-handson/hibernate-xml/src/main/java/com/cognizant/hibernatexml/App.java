package com.cognizant.hibernatexml;

import com.cognizant.hibernatexml.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class App {
    private static SessionFactory factory;

    public static void main(String[] args) {
        System.out.println("=== Starting Hibernate XML Configuration Demo ===");
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }

        App app = new App();

        // 1. Add records
        System.out.println("\n--- Saving Books via XML Configuration ---");
        Long bookId1 = app.addBook("Spring Boot in Action (XML)", new BigDecimal("49.99"), LocalDate.of(2020, 5, 20));
        Long bookId2 = app.addBook("Java Concurrency in Practice (XML)", new BigDecimal("59.90"), LocalDate.of(2006, 5, 9));
        Long bookId3 = app.addBook("Effective Java (XML)", new BigDecimal("45.00"), LocalDate.of(2018, 1, 11));

        // 2. Query records
        System.out.println("\n--- Querying Books via HQL ---");
        app.listBooks();

        // 3. Clean up
        factory.close();
        System.out.println("\n=== Finished Hibernate XML Configuration Demo ===");
    }

    public Long addBook(String title, BigDecimal price, LocalDate publishDate) {
        Session session = factory.openSession();
        Transaction tx = null;
        Long bookID = null;

        try {
            tx = session.beginTransaction();
            Book book = new Book(title, price, publishDate);
            bookID = (Long) session.save(book);
            tx.commit();
            System.out.println("Successfully saved Book [ID: " + bookID + ", Title: '" + title + "']");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return bookID;
    }

    @SuppressWarnings("unchecked")
    public void listBooks() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<Book> books = session.createQuery("FROM Book").list();
            for (Book book : books) {
                System.out.println("Fetched: " + book);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
