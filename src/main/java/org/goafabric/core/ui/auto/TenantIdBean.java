package org.goafabric.core.ui.auto;

import org.goafabric.core.data.extensions.HttpInterceptor;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RegisterReflectionForBinding(TenantIdBean.class)
public class TenantIdBean {
    @Value("${multi-tenancy.schema-prefix:_}") String schemaPrefix;

    public String getPrefix() {
        return schemaPrefix + HttpInterceptor.getTenantId() + "_";
    }
}
