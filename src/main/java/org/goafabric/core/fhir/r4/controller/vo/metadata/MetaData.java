
package org.goafabric.core.fhir.r4.controller.vo.metadata;

import java.util.List;

public class MetaData {
    public String resourceType;
    public String id;
    public String name;
    public String status;
    public String date;
    public String publisher;
    public String kind;
    public Software software;
    public Implementation implementation;
    public String fhirVersion;
    public List<String> format;
    public List<Rest> rest;
}
