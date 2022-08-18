package com.jhones.booklist.controller;

import com.jhones.booklist.model.Book;
import com.jhones.booklist.model.BookList;
import com.jhones.booklist.service.implementation.BookListImplementation;
import com.jhones.booklist.service.implementation.BookServiceImplementation;
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
import java.util.List;

@Controller
public class BookController {

    //Dependency Injections
    @Autowired
    BookServiceImplementation bookService;

    @Autowired
    BookListImplementation bookListImplementation;

    // Routes


    //Loads the save a book view
    @RequestMapping(value = "/book/create/{id}", method = RequestMethod.GET)
    public String loadSaveBookForm(@PathVariable("id") long id){
        return ("/saveBookForm");
    }

    //Saves the book according to its specific list
    @RequestMapping(value = "/book/create/{id}", method = RequestMethod.POST)
    public String saveBook(@PathVariable("id") long id, @Valid Book book, BindingResult result, RedirectAttributes attributes){
        BookList bookList = bookListImplementation.findById(id);
        book.setBookList(bookList);
        if(result.hasErrors()){
            attributes.addFlashAttribute("message","All the fields must be filled");
            return "redirect:/book/create/"+bookList.getId();
        }
        bookService.save(book);
        return "redirect:/booklist";
    }

    //Lists all the book from a specific book list
    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public ModelAndView listBooks(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView("myBooks");
        List<Book> books = bookService.findAllById(id);
        BookList bookList = bookListImplementation.findById(id);

        modelAndView.addObject("books",books);
        modelAndView.addObject("blist",bookList);


        //Get the current user information
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            System.out.println(username);
        } else {
            String username = principal.toString();
            System.out.println(username);
        }


        return modelAndView;
    }

    @RequestMapping(value = "/book/delete")
    public String deleteBook(long id){
        Book book = bookService.findById(id);
        bookService.delete(book);
        return "redirect:/books/"+book.getBookList().getId();
    }

    //Load update view and populates it with the book data
    @RequestMapping(value = "/book/update/{id}", method = RequestMethod.GET)
    public ModelAndView loadUpdateBookForm(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView("updateBookForm");
        Book book = bookService.findById(id);
        modelAndView.addObject("books",book);
        return modelAndView;
    }

    //Updates the book on database
    @RequestMapping(value = "/book/update/{id}", method = RequestMethod.POST)
    public String updateBook(@PathVariable("id") long id, Book book){
        Book oldBook = bookService.findById(id);
        book.setBookList(oldBook.getBookList());
        bookService.save(book);
        return "redirect:/booklist";
    }

    //List the Book Detail View
    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public ModelAndView listBookDetails(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView("bookDetails");
        Book book = bookService.findById(id);
        modelAndView.addObject("book",book);
        return modelAndView;
    }

}
