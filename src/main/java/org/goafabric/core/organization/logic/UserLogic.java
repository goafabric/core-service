package org.goafabric.core.organization.logic;

import org.goafabric.core.extensions.TenantContext;
import org.goafabric.core.organization.controller.dto.UserInfo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserLogic {

    public UserInfo getUserInfo() {
        return new UserInfo(TenantContext.getUserName(), TenantContext.getTenantId());
    }
}
