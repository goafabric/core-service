package org.goafabric.core.fhir.logic;

import java.util.List;

public interface FhirLogic<T> {

    void create(T t);

    void delete(String id);

    T getById(String id);

    List<T> search(String search);
}
