
package org.goafabric.core.fhir.r4.controller.vo.metadata;

import java.util.List;

public class Resource {
    public String type;
    public String profile;
    public List<Interaction> interaction;
    public List<String> searchInclude;
    public List<SearchParam> searchParam;
}
