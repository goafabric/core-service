package org.goafabric.core.extensions;

import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jwt.JWTParser;
import io.micrometer.common.KeyValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.ServerHttpObservationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;


@Component
public class HttpInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private static final ThreadLocal<String> tenantId = new ThreadLocal<>();
    private static final ThreadLocal<String> userName = new ThreadLocal<>();

    /*
    private static Boolean isAuthenticationEnabled;
    @Value("${security.authentication.enabled}") void setAuthenticationEnabled(Boolean authenticationEnabled) {
        isAuthenticationEnabled = authenticationEnabled;
    }
     */

    @Configuration
    static class Configurer implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new HttpInterceptor());
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        tenantId.set(request.getHeader("X-TenantId"));
        configureAuthenticationViaJWT(request.getHeader("X-Access-Token"));
        configureLogsAndTracing(request);
        if (handler instanceof HandlerMethod) {
            log.info(" {} method called for user {} ", ((HandlerMethod) handler).getShortLogMessage(), getUserName());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        tenantId.remove();
        userName.remove();
        MDC.remove("tenantId");
    }

    private static void configureAuthenticationViaJWT(String token) {
        if (token != null) {
            var payload = decodeJwt(token);
            Objects.requireNonNull(payload.get("preferred_username"), "Username in JWT is null");
            userName.set(payload.get("preferred_username").toString());
            //while we could get the tenant from the issuer like this, we shouldn't instead either via X-TenantId passed from Api6
            //tenantId.set(payload.get("iss").toString().split("realms/")[1].replace("tenant-", ""));
        }
    }

    private static void configureLogsAndTracing(HttpServletRequest request) {
        MDC.put("tenantId", getTenantId());
        ServerHttpObservationFilter.findObservationContext(request).ifPresent(
                context -> context.addHighCardinalityKeyValue(KeyValue.of("tenant.id", getTenantId())));
    }

    public static String getTenantId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof OAuth2AuthenticationToken ? ((OAuth2AuthenticationToken)auth).getAuthorizedClientRegistrationId()
                : "0";
    }

    public static String getUserName() {
        return (SecurityContextHolder.getContext().getAuthentication() != null) && !(SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
                ? SecurityContextHolder.getContext().getAuthentication().getName() : userName.get();
        //return isAuthenticationEnabled ? SecurityContextHolder.getContext().getAuthentication().getName() : userName.get();
        //yo
    }

    private static Map<String, Object> decodeJwt(String token) {
        try {
            return ((JOSEObject) JWTParser.parse(token)).getPayload().toJSONObject();
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }

}