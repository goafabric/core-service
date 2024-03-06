package org.goafabric.core.organization.persistence.extensions;

import org.goafabric.core.extensions.HttpInterceptor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.stereotype.Component;

@Component
@RegisterReflectionForBinding(TenantIdBean.class)
public class TenantIdBean {
    public String getPrefix() {
        return "core-" + HttpInterceptor.getTenantId() + "-";
    }
}
