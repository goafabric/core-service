package org.goafabric.core.fhir.controller.vo;

import java.util.ArrayList;
import java.util.List;

public class Bundle<T> {
    public String id;
    public final String resourceType = "Bundle";

    public final List<BundleEntryComponent<T>> entry = new ArrayList<>();

    public void addEntry(BundleEntryComponent bundleEntry) {
        entry.add(bundleEntry);
    }

    public static class BundleEntryComponent<T> {
        public String fullUrl;
        public T resource;
    }

}
