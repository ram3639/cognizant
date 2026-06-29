package com.library.service;

import com.library.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;

    // Setter method for Dependency Injection (XML Setter Injection)
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[BookService] BookRepository injected successfully via setter injection.");
    }

    public void addBook(String bookTitle) {
        System.out.println("[BookService] Attempting to add book: \"" + bookTitle + "\"");
        if (bookRepository != null) {
            bookRepository.saveBook(bookTitle);
        } else {
            System.err.println("[BookService] Error: BookRepository is not initialized!");
        }
    }
}
