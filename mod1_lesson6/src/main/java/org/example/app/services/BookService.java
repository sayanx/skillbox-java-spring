package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public List<Book> getFilteredBooks(Book params) {
        return bookRepo.retreiveFiltered(params);
    }

    public void saveBook(Book book) {
        if (!book.getTitle().isEmpty() || !book.getAuthor().isEmpty() || book.getSize() != null) {
            bookRepo.store(book);
        }
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByParams(Book book) {
        return bookRepo.removeItemByParams(book);
    }
}
