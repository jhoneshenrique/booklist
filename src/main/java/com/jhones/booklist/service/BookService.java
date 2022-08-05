package com.jhones.booklist.service;

import com.jhones.booklist.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    //Custom query

    List<Book> findAllById(Long id);
    Book findById(long id);
    Book save(Book book);

    void delete(Book book);
}
