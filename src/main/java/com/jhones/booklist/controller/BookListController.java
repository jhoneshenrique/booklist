package com.jhones.booklist.controller;

import com.jhones.booklist.model.Book;
import com.jhones.booklist.model.BookList;
import com.jhones.booklist.model.User;
import com.jhones.booklist.service.implementation.BookListImplementation;
import com.jhones.booklist.service.implementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class BookListController {

    //------------ Dependency Injection--------------------------------------------------------
    @Autowired
    BookListImplementation bookListImpleme;

    @Autowired
    UserServiceImplementation userService;


    //Load create booklist view
    @RequestMapping(value = "/booklist/create", method = RequestMethod.GET)
    public String loadSaveBookListForm(){
        return ("/saveBookListForm");
    }

    //Add a booklist to database
    @RequestMapping(value = "/booklist/create", method = RequestMethod.POST)
    public String saveBookList(@Valid BookList bookList, BindingResult result, RedirectAttributes attributes){
        bookList.setDateCreation(LocalDate.now());
        //Get the current user information
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userService.findByLogin(username);
        bookList.setUser(user);
        if(result.hasErrors()){
            attributes.addFlashAttribute("message","All the fields must be filled");
            return "redirect:/booklist/create";
        }
        bookListImpleme.save(bookList);
        return "redirect:/booklist";
    }

    //List all the booklists from db and send to the view
    @RequestMapping(value = "/booklist", method = RequestMethod.GET)
    public ModelAndView listBooklist(){
        ModelAndView modelAndView = new ModelAndView("myLists");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        Iterable<BookList> booklist = bookListImpleme.findAllById(username);
        modelAndView.addObject("booklist",booklist);
        return modelAndView;
    }

    //Deleta a booklist from DB
    @RequestMapping(value = "/booklist/delete", method = RequestMethod.GET)
    public String deleteList(long id){
        BookList bookList = bookListImpleme.findById(id);
        bookListImpleme.delete(bookList);
        return "redirect:/booklist";
    }

    //Load the Update List form and the data
    @RequestMapping(value = "/booklist/update/{id}", method = RequestMethod.GET)
    public ModelAndView loadUpdateBookListForm(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView("updateBookListForm");
        BookList booklist = bookListImpleme.findById(id);
        modelAndView.addObject("booklist",booklist);
        return modelAndView;
    }

    //Update the list on the database
    @RequestMapping(value = "/booklist/update/{id}", method = RequestMethod.POST)
    public String updateBookList(@PathVariable("id") long id, BookList bookList){
        bookList.setDateCreation(LocalDate.now());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = userService.findByLogin(username);
        bookList.setUser(user);

        bookListImpleme.save(bookList);
        return "redirect:/booklist";
    }



}
