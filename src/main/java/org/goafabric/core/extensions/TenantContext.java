package org.goafabric.core.extensions;

import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

public class TenantContext {
    private static final ThreadLocal<TenantContextRecord> tenantContext
            = ThreadLocal.withInitial(() -> new TenantContextRecord(null, null, null, null));

    public record TenantContextRecord(String tenantId, String organizationId, String authToken, String userName)  {}

    public static void setContext(HttpServletRequest request) {
        setContext(new TenantContextRecord(
                getTenantIdFromTokenOrTenant(request.getHeader("X-Userinfo"), request.getHeader("X-TenantId")),
                request.getHeader("X-OrganizationId"),
                request.getHeader("X-Access-Token"),
                getUserNameFromToken(request.getHeader("X-Access-Token"))
                )); //request.getHeader("Authorization").substring(7)));
    }

    static void setContext(TenantContextRecord tenantContextRecord) {
        tenantContext.set(tenantContextRecord);
    }

    public static void removeContext() {
        tenantContext.remove();
    }

    public static void setTenantId(String tenantId) {
        setContext(new TenantContextRecord(tenantId, tenantContext.get().organizationId, tenantContext.get().authToken, tenantContext.get().userName));
    }

    public static String getTenantId() {
        return getAuthentication() instanceof OAuth2AuthenticationToken ? ((OAuth2AuthenticationToken)getAuthentication()).getAuthorizedClientRegistrationId()
                : tenantContext.get().tenantId() != null ? tenantContext.get().tenantId() : "0";
    }

    public static String getOrganizationId() {
        return tenantContext.get().organizationId() != null ? tenantContext.get().organizationId() : "1";
    }

    public static String getUserName() {
        return (getAuthentication() != null) && !(getAuthentication().getName().equals("anonymousUser")) ? getAuthentication().getName()
                : tenantContext.get().userName != null ? tenantContext.get().userName : "anonymous";
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static String getTenantIdFromTokenOrTenant(String userInfoToken, String tenantId) {
        String tenantFromUserInfo = null; //todo retrieve tenant from Userinfo
        return tenantFromUserInfo != null ? tenantFromUserInfo  : tenantId;
    }

    static String getUserNameFromToken(String authToken) {
        if (authToken != null) {
            var payload = decodeJwt(authToken);
            Objects.requireNonNull(payload.get("preferred_username"), "preferred_username in JWT is null");
            return payload.get("preferred_username").toString();
        }
        return null;
    }

    private static Map<String, Object> decodeJwt(String token) {
        try {
            return ((JOSEObject) JWTParser.parse(token)).getPayload().toJSONObject();
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

}
