package com.cognizant.springdatajpah2.repository;

import com.cognizant.springdatajpah2.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Custom finder method that generates query automatically based on method name!
    List<Book> findByTitleContaining(String title);
}
