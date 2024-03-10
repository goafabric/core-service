package org.goafabric.core.extensions;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TenantContextTest {

    @Test
    public void getTenantId() {
        TenantContext.setContext(new TenantContext.TenantContextRecord("42", null, null, null));
        assertThat(TenantContext.getTenantId()).isEqualTo("42");

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, null, null));
        assertThat(TenantContext.getTenantId()).isEqualTo("0");
    }

    @Test
    public void getOrganaizationId() {
        TenantContext.setContext(new TenantContext.TenantContextRecord(null, "44", null, null));
        assertThat(TenantContext.getOrganizationId()).isEqualTo("44");

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, null, null));
        assertThat(TenantContext.getOrganizationId()).isEqualTo("0");
    }

    @Test
    public void getUserName() {
        var authToken = "Bearer "
                + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiSm9obiBEb2VYIiwiaWF0IjoxNTE2MjM5MDIyfQ.p0AyXKhUgzn2FhcgB7xeGVqUbYZRfL_Gdo6xk2uf3rY";

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, authToken, TenantContext.getUserNameFromToken(authToken)));
        assertThat(TenantContext.getUserName()).isEqualTo("John DoeX");

        TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, null, null));
        assertThat(TenantContext.getUserName()).isEqualTo("anonymous");

        assertThatThrownBy(() ->
                TenantContext.setContext(new TenantContext.TenantContextRecord(null, null, "invalidtoken", TenantContext.getUserNameFromToken("invalid")))
        ).isInstanceOf(IllegalStateException.class);

    }

    @Test
    public void getAll() {
        TenantContext.setContext(new TenantContext.TenantContextRecord("42", "44", "token", "user"));

        Assertions.assertThat(TenantContext.getTenantId()).isEqualTo("42");
        Assertions.assertThat(TenantContext.getOrganizationId()).isEqualTo("44");
        Assertions.assertThat(TenantContext.getUserName()).isEqualTo("user");
        Assertions.assertThat(TenantContext.getAdapterHeaderMap()).isNotNull().isNotEmpty();

    }

    /*
    @Test
    public void userINfo() throws ParseException {
        var userInfo = "eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwidXJuOmdvYWZhYnJpYzpjbGFpbXM6aW5zdGl0dXRpb24iOlt7ImlkIjoiODJmYWNkZGUtMTk1Yy00NzhlLTg3M2EtMjNjMzQwZmZmMmVkIiwicm9sZXMiOlsiTUVNQkVSIiwiQURNSU4iXSwibmFtZSI6IlRlc3QifV0sIm5hbWUiOiJKb2huIERvZSIsInByZWZlcnJlZF91c2VybmFtZSI6ImpvaG4uZG9lQHVua25vd24uY29tIiwiZW1haWwiOiJqb2huLmRvZUB1bmtub3duLmNvbSIsImFsZyI6IkhTMjU2In0.srccIrNps9i093rcqTKEVLzWUuoJMcEjrmdKjhNFlPE";
        JWTClaimsSet claimsSet = JWTParser.parse(userInfo).getJWTClaimsSet();
        List claim = (List) claimsSet.getClaim("urn:goafabric:claims:institution");
        String id = ((Map) claim.getFirst()).get("id").toString();
        int x = 5;
    }

     */

    /*
    @Test
    public void createSimpleToken() {
        var token = new PlainJWT(new JWTClaimsSet.Builder().claim("preferred_username", TenantContext.getUserName()).build()).serialize();
        System.err.println(token);
        System.err.println(TenantContext.getUserNameFromToken("Bearer " + token));

    }
     */

}