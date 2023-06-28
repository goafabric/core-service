package org.goafabric.core.ui;

import java.util.List;

public interface SearchLogic<T> {
    List<T> search(String search);
}
