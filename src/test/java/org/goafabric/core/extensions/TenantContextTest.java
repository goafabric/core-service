package org.goafabric.core.extensions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class TenantContextTest {

    @Test
    public void getTenantId() {
        TenantContext.setContext(new TenantContext.TenantContextRecord("42", null, null));
        assertThat(TenantContext.getTenantId()).isEqualTo("42");

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, null));
        assertThat(TenantContext.getTenantId()).isEqualTo("0");
    }

    @Test
    public void getOrganaizationId() {
        TenantContext.setContext(new TenantContext.TenantContextRecord(null, "44", null));
        assertThat(TenantContext.getOrganizationId()).isEqualTo("44");

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, null));
        assertThat(TenantContext.getOrganizationId()).isEqualTo("1");
    }

    @Test
    public void getUserName() {
        var authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiSm9obiBEb2VYIiwiaWF0IjoxNTE2MjM5MDIyfQ.p0AyXKhUgzn2FhcgB7xeGVqUbYZRfL_Gdo6xk2uf3rY";

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, authToken));
        assertThat(TenantContext.getUserName()).isEqualTo("John DoeX");

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, null));
        assertThat(TenantContext.getUserName()).isEqualTo(null);

        assertThatThrownBy(() ->
                TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, "invalidtoken"))
        ).isInstanceOf(IllegalStateException.class);

    }


}