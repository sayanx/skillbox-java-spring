package org.example.app.services;

import org.example.web.dto.Book;
import org.example.web.dto.BookFilter;
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

    public List<Book> getFilteredBooks(BookFilter filter) {
        return bookRepo.retreiveFiltered(filter);
    }

    public void saveBook(Book book) {
        if (!book.getTitle().isEmpty() || !book.getAuthor().isEmpty() || book.getSize() != null) {
            bookRepo.store(book);
        }
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByParams(BookFilter filter) {
        return bookRepo.removeItemByParams(filter);
    }
}
