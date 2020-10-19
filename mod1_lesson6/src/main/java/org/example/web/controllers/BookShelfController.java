package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(
            Model model,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false, defaultValue = "") String author,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false) Integer size
    ) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookFilter", new BookFilter());

        BookFilter filter = new BookFilter(id, author, title, size);
        logger.info("before filters" + filter);
        model.addAttribute("bookList", bookService.getFilteredBooks(filter));
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(BookFilter filter, Model model) {
        if (bookService.removeBookByParams(filter)) {
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookFilter", new BookFilter());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("hasRemoveError", true);
            return "book_shelf";
        }
    }
}
