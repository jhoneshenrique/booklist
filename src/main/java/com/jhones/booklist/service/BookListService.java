package com.jhones.booklist.service;

import com.jhones.booklist.model.Book;import com.jhones.booklist.model.BookList;

import java.util.List;

public interface BookListService {
    List<BookList> findAll();
    BookList findById(long id);
    BookList save(BookList bookList);

    void delete(BookList bookList);
}