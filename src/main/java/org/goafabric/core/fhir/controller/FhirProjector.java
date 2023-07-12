package org.goafabric.core.fhir.controller;

import org.goafabric.core.fhir.controller.vo.Bundle;

public interface FhirProjector<T> {

    void create(T t);

    void delete(String id);

    T getById(String id);

    Bundle<T> search(String search);
}
