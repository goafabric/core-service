package org.goafabric.core.ui.adapter;

import java.util.List;

public interface SearchAdapter<T> {
    List<T> search(String search);

    default void save (T item) {}

    default void delete(String id) {}
}
