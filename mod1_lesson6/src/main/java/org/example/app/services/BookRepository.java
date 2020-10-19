package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookFilter;
import org.example.web.dto.Filter;
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

    private boolean isBookMatchesByParams(Book book, BookFilter filter) {
        boolean isMatches = false;

        logger.info("isBookMatchesByParams filter: " + filter);

        Integer idToDelete = filter.getId();
        if (book.getId().equals(idToDelete)) {
            isMatches = true;
        }

        String authorToDelete = filter.getAuthor();
        logger.info("authorToDelete:" + authorToDelete);
        if (!authorToDelete.isEmpty()) {
            isMatches = book.getAuthor().matches("(.*)" + authorToDelete + "(.*)");
        }

        String titleToDelete = filter.getTitle();
        logger.info("titleToDelete:" + titleToDelete);
        if (!titleToDelete.isEmpty()) {
            isMatches = book.getTitle().matches("(.*)" + titleToDelete + "(.*)");
        }

        Integer sizeToDelete = filter.getSize();
        if (sizeToDelete != null) {
            isMatches = book.getSize().equals(sizeToDelete);
        }

        return isMatches;
    }

    @Override
    public List<Book> retreiveFiltered(Filter filter) {
        List<Book> result = new ArrayList<>();

        for (Book book : repo) {
            if (filter.isEmpty() || isBookMatchesByParams(book, (BookFilter) filter)) {
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
    public boolean removeItemByParams(Filter filter) {
        logger.info("removeItemByParams: " + filter);
        for (Book book : retreiveAll()) {
            if (isBookMatchesByParams(book, (BookFilter) filter)) {
                repo.remove(book);
                return true;
            }
        }
        return false;
    }
}
