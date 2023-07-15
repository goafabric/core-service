package org.goafabric.core.fhir.r4;

import org.goafabric.core.fhir.r4.controller.vo.Bundle;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@RegisterReflectionForBinding({Bundle.class, Bundle.BundleEntryComponent.class})
public class NativeConfiguration {
}
