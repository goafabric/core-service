package org.goafabric.core.fhir.projector;

import org.goafabric.core.fhir.projector.vo.Bundle;

public interface FhirProjector<T> {

    void create(T t);

    void delete(String id);

    T getById(String id);

    Bundle<T> search(String search);
}
