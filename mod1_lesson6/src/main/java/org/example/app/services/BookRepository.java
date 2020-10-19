package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    private boolean isBookMatchesByParams(Book book, Book params) {
        boolean isMatches = false;

        Integer idToDelete = params.getId();
        if (book.getId().equals(idToDelete)) {
            isMatches = true;
        }

        String authorToDelete = params.getAuthor();
        if (!authorToDelete.isEmpty()) {
            isMatches = book.getAuthor().matches("(.*)" + authorToDelete + "(.*)");
        }

        String titleToDelete = params.getAuthor();
        if (!titleToDelete.isEmpty()) {
            isMatches = book.getTitle().matches("(.*)" + titleToDelete + "(.*)");
        }

        Integer sizeToDelete = params.getSize();
        if (sizeToDelete != null) {
            isMatches = book.getSize().equals(sizeToDelete);
        }

        return isMatches;
    }

    @Override
    public List<Book> retreiveFiltered(Book params) {
        List<Book> result = new ArrayList<>();

        for (Book book : repo) {
            if (params.isEmpty() || isBookMatchesByParams(book, params)) {
                result.add(book);
            }
        }

        return result;
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        logger.info("removeItemById: " + bookIdToRemove);
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByParams(Book params) {
        logger.info("removeItemByParams: " + params);
        for (Book book : retreiveAll()) {
            if (isBookMatchesByParams(book, params)) {
                repo.remove(book);
                return true;
            }
        }
        return false;
    }
}
