package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
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

        Book params = new Book(id, author, title, size);
        logger.info("bewfore filters" + params);
        model.addAttribute("bookList", bookService.getFilteredBooks(params));
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(Book book, Model model) {
        if (bookService.removeBookByParams(book)) {
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            model.addAttribute("hasRemoveError", true);
            return "book_shelf";
        }
    }
}
