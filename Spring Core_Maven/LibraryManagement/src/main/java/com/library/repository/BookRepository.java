package com.library.repository;

public class BookRepository {
    public void saveBook(String bookTitle) {
        System.out.println("[BookRepository] Saving book to database: \"" + bookTitle + "\"");
    }
}
