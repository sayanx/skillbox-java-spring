package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    List<T> retreiveFiltered(T book);

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByParams(T book);
}
