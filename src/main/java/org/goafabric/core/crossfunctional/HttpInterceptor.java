package org.goafabric.core.crossfunctional;

import io.micrometer.common.KeyValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.filter.ServerHttpObservationFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class HttpInterceptor implements WebMvcConfigurer {
    private static final ThreadLocal<String> tenantId = new ThreadLocal<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                configureLogsAndTracing(getTenantId(), request);
                return true;
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                tenantId.remove();
                MDC.remove("tenantId");
            }

            private static void configureLogsAndTracing(String tenantId, HttpServletRequest request) {
                if (tenantId != null && request != null) {
                    MDC.put("tenantId", tenantId);
                    ServerHttpObservationFilter.findObservationContext(request).ifPresent(
                            context -> context.addHighCardinalityKeyValue(KeyValue.of("tenantId.id", tenantId)));
                }
            }

        });
    }

    public static void setTenantId(String tenantId) {
        HttpInterceptor.tenantId.set(tenantId);
    }

    public static String getTenantId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof OAuth2AuthenticationToken ? ((OAuth2AuthenticationToken)auth).getAuthorizedClientRegistrationId()
                : tenantId.get() != null ? tenantId.get() : "0";
    }

    public static String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : "";
    }

    public static String getCompanyId() {
        return "1";
    }

}