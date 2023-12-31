package org.goafabric.core.fhir.r4.controller;

import org.goafabric.core.fhir.r4.controller.dto.Bundle;

public interface FhirProjector<T> {

    void create(T t);

    void delete(String id);

    T getById(String id);

    Bundle<T> search(String search);
}
