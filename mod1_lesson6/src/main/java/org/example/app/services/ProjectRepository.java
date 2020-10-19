package org.example.app.services;

import org.example.web.dto.Filter;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    List<T> retreiveFiltered(Filter book);

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByParams(Filter book);
}
