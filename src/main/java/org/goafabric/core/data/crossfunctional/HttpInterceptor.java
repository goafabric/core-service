package org.goafabric.core.data.crossfunctional;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class HttpInterceptor implements WebMvcConfigurer {
    private static final ThreadLocal<String> tenantId = new ThreadLocal<>();
    private static final ThreadLocal<String> userName = new ThreadLocal<>();

    /*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                //tenantId.set(request.getHeader("X-TenantId"));
                //userName.set(request.getHeader("X-Auth-Request-Preferred-Username"));
                return true;
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                tenantId.remove();
                userName.remove();
            }
        });
    }

     */


    public static String getTenantId() {
        if (tenantId.get() != null) {
            return tenantId.get();
        }
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof OAuth2AuthenticationToken ? ((OAuth2AuthenticationToken)auth).getAuthorizedClientRegistrationId() : "0";
    }

    public static String getUserName() {
        return userName.get() != null ? userName.get()
                : SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : "";
    }

    public static void setTenantId(String tenantId) {
        HttpInterceptor.tenantId.set(tenantId);
    }

    public static String getCompanyId() {
        return "1";
    }

}