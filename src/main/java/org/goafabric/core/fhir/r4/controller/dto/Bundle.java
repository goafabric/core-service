package org.goafabric.core.fhir.r4.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class Bundle<T> {
    public String id;
    public final String resourceType = "Bundle";

    public List<BundleEntryComponent<T>> entry = new ArrayList<>();

    public Bundle(List<BundleEntryComponent<T>> entry) {
        this.entry = entry;
    }

    public Bundle() {
    }

    public static class BundleEntryComponent<T> {
        public T resource;

        public BundleEntryComponent(T resource, String fullUrl) {
            this.resource = resource;
            this.fullUrl = fullUrl;
        }

        public String fullUrl;
    }

}
