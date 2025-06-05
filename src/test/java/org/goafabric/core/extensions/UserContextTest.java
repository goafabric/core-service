package org.goafabric.core.extensions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserContextTest {

    @Test
    void getTenantId() {
        UserContext.setContext(null, null, null, null);
        assertThat(UserContext.getTenantId()).isEqualTo("0");
    }

    @Test
    void getOrganizationId() {
        UserContext.setContext(null, null, null, null);
        assertThat(UserContext.getOrganizationId()).isEqualTo("0");
    }

    @Test
    void getUserName() {
        UserContext.setContext(null, null, null, null);
        assertThat(UserContext.getUserName()).isEqualTo("anonymous");
    }
    
    @Test
    void setTenantId() {
        UserContext.setContext(null, null, null, null);
        UserContext.setTenantId("42");
        assertThat(UserContext.getTenantId()).isEqualTo("42");
    }

    @Test
    void getUserNameFromUserINfo() {
        var userInfo = "eyJwcmVmZXJyZWRfdXNlcm5hbWUiOiJqb2huIGRvZSIsImFsZyI6IkhTMjU2In0";
        UserContext.setContext("42", "44", "user", userInfo);
        assertThat(UserContext.getUserName()).isEqualTo("john doe");
    }

    @Test
    void getAll() {
        UserContext.setContext("42", "44", "user", null);
        assertThat(UserContext.getTenantId()).isEqualTo("42");
        assertThat(UserContext.getOrganizationId()).isEqualTo("44");
        assertThat(UserContext.getUserName()).isEqualTo("user");
        assertThat(UserContext.getAdapterHeaderMap()).isNotNull().isNotEmpty();
    }


}