package com.jhones.booklist.controller;

import com.jhones.booklist.model.Book;
import com.jhones.booklist.model.BookList;
import com.jhones.booklist.service.implementation.BookListImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class BookListController {

    //Dependency Injection
    @Autowired
    BookListImplementation bookListImpleme;

    //Load create booklist view
    @RequestMapping(value = "/booklist/create", method = RequestMethod.GET)
    public String loadSaveBookListForm(){
        return ("/saveBookListForm");
    }

    //Add a booklist to database
    @RequestMapping(value = "/booklist/create", method = RequestMethod.POST)
    public String saveBookList(BookList bookList){
        bookList.setDateCreation(LocalDate.now());
        bookListImpleme.save(bookList);
        return "redirect:/booklist";
    }

    //List all the booklists from db and send to the view
    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    public ModelAndView listBooklist(){
        ModelAndView modelAndView = new ModelAndView("myLists");
        Iterable<BookList> booklist = bookListImpleme.findAll();
        modelAndView.addObject("booklist",booklist);
        return modelAndView;
    }

    @RequestMapping(value = "/booklist/delete", method = RequestMethod.GET)
    public String deleteList(long id){
        BookList bookList = bookListImpleme.findById(id);
        bookListImpleme.delete(bookList);
        return "redirect:/booklist";
    }



}
