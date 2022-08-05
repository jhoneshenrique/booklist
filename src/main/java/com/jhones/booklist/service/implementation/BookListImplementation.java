package com.jhones.booklist.service.implementation;

import com.jhones.booklist.model.BookList;
import com.jhones.booklist.repository.BookListRepository;
import com.jhones.booklist.service.BookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookListImplementation implements BookListService {

    @Autowired
    BookListRepository bookListRepository;

    @Override
    public List<BookList> findAll() {
        return bookListRepository.findAll();
    }

    @Override
    public BookList findById(long id) {
        return bookListRepository.findById(id).get();
    }

    @Override
    public BookList save(BookList bookList) {
        return bookListRepository.save(bookList);
    }

    @Override
    public void delete(BookList bookList) {
        bookListRepository.delete(bookList);
    }
}
