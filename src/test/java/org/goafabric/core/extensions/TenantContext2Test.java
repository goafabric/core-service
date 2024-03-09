package org.goafabric.core.extensions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TenantContext2Test {
    @Test
    public void getOrganizationId() {
        TenantContext2.setContext(new TenantContext2.TenantContextRecord("42", null, null, null, null));
        assertThat(TenantContext2.getOrganizationId()).isEqualTo("42");

        TenantContext2.setContext(new TenantContext2.TenantContextRecord(null, null, null, null, null));
        assertThat(TenantContext2.getOrganizationId()).isEqualTo("1");
    }

    @Test
    public void getTenantId() {

        TenantContext2.setContext(new TenantContext2.TenantContextRecord(null, "44", null, null, null));
        assertThat(TenantContext2.getTenantId()).isEqualTo("44");

        TenantContext2.setContext(new TenantContext2.TenantContextRecord(null, null, null, null, null));
        assertThat(TenantContext2.getTenantId()).isEqualTo("0");
    }

    @Test
    public void getUserName() {
        var authToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiSm9obiBEb2VYIiwiaWF0IjoxNTE2MjM5MDIyfQ.p0AyXKhUgzn2FhcgB7xeGVqUbYZRfL_Gdo6xk2uf3rY";

        TenantContext2.setContext(new TenantContext2.TenantContextRecord(null, null, null, authToken));
        assertThat(TenantContext2.getUserName()).isEqualTo("John DoeX");

        TenantContext2.setContext(new TenantContext2.TenantContextRecord(null, null, null, null));
        assertThat(TenantContext2.getUserName()).isEqualTo("");

        assertThatThrownBy(() ->
                TenantContext2.setContext(new TenantContext2.TenantContextRecord(null, null, null, "invalid"))
        ).isInstanceOf(IllegalStateException.class);

    }

    @Test
    public void userInfo() {
        var userInfo = "eyJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwidXJuOmdvYWZhYnJpYzpjbGFpbXM6aW5zdGl0dXRpb24iOlt7ImlkIjoiNSIsInJvbGVzIjpbIk1FTUJFUiIsIkFETUlOIl0sIm5hbWUiOiJUZXN0In1dLCJuYW1lIjoiSm9obiBEb2UiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJqb2huLmRvZUB1bmtub3duLmNvbSIsImVtYWlsIjoiam9obi5kb2VAdW5rbm93bi5jb20iLCJhbGciOiJIUzI1NiJ9.e30.Eo5oEgkDJITGZ23MXXW4Car87_qcLqBKuE-KvFuEYJU";
        TenantContext2.setContext(new TenantContext2.TenantContextRecord(null, null, userInfo, null));
    }

}