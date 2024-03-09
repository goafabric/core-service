package org.goafabric.core.extensions;

import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

public class TenantContext {
    private static final ThreadLocal<TenantContextRecord> tenantContext
            = ThreadLocal.withInitial(() -> new TenantContextRecord(null, null, null, null));

    public record TenantContextRecord(String tenantId, String organizationId, String authToken, String userName)  {

        TenantContextRecord(String tenantId, String organizationId, String authToken) {
            this(tenantId, organizationId, authToken, getUserNameFromToken(authToken));
        }
    }

    public static void setContext(HttpServletRequest request) {
        tenantContext.set(new TenantContextRecord(request.getHeader("X-OrganizationId"), request.getHeader("X-TenantId"),
                request.getHeader("Authorization").substring(7)));
    }

    static void setContext(TenantContextRecord tenantContextRecord) {
        tenantContext.set(tenantContextRecord);
    }

    public static String getTenantId() {
        return tenantContext.get().tenantId() != null ? tenantContext.get().tenantId() : "0";
    }

    public static String getOrganizationId() {
        return tenantContext.get().organizationId() != null ? tenantContext.get().organizationId() : "1";
    }

    public static String getUserName() {
        return (SecurityContextHolder.getContext().getAuthentication() != null) && !(SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
                ? SecurityContextHolder.getContext().getAuthentication().getName() : tenantContext.get().userName;
    }
    
    private static String getUserNameFromToken(String token) {
        var attribute = "preferred_username";
        if (token != null) {
            var payload = decodeJwt(token);
            Objects.requireNonNull(payload.get(attribute), attribute + " in JWT is null");
            return payload.get(attribute).toString();
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
