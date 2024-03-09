package org.goafabric.core.extensions;

import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

public class TenantContext2 {
    private static final ThreadLocal<TenantContextRecord> tenantContext
            = ThreadLocal.withInitial(() -> new TenantContextRecord(null, null, null, null, null));

    public record TenantContextRecord(String organizationId, String tenantId, String userInfoToken, String authToken, String userName)  {
        TenantContextRecord(String organizationId, String tenantId, String userInfoToken, String authToken) {
            this(organizationId, getTenantId(userInfoToken, tenantId), userInfoToken, authToken, getUserName(authToken));
        }
    }

    public static void setContext(HttpServletRequest request) {
        tenantContext.set(new TenantContextRecord(request.getHeader("X-OrganizationId"), request.getHeader("X-TenantId"),
                request.getHeader("X-UserInfo"), request.getHeader("Authorization").substring(7)));
    }

    public static void setContext(TenantContextRecord tenantContextRecord) {
        tenantContext.set(tenantContextRecord);
    }

    public static String getTenantId() {
        return tenantContext.get().tenantId() != null ? tenantContext.get().tenantId() : "0";
    }

    public static String getOrganizationId() {
        return tenantContext.get().organizationId() != null ? tenantContext.get().organizationId() : "1";
    }


    public static String getUserName() {
        return tenantContext.get().userName != null ? tenantContext.get().userName
                : SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : "";
    }

    /**/
    private static String getTenantId(String token, String tenantId) {
        return tenantId;
    }

    private static String getUserName(String authToken) {
        return getAttributeFromJwt(authToken, "preferred_username");
    }

    private static String getAttributeFromJwt(String token, String attribute) {
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
