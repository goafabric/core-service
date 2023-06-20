package org.goafabric.core.crossfunctional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SecurityConfiguration {
    @Value("${security.authentication.enabled}") private Boolean isAuthenticationEnabled;

    @Value("${spring.security.oauth2.base-uri}") private String baseUri;
    @Value("${spring.security.oauth2.frontend-uri}") private String frontendUri;
    @Value("${spring.security.oauth2.prefix:}") private String prefix;


    @Value("${spring.security.oauth2.client-id}") private String clientId;
    @Value("${spring.security.oauth2.client-secret}") private String clientSecret;
    @Value("${spring.security.oauth2.user-name-attribute:}") private String userNameAttribute;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TenantClientRegistrationRepository clientRegistrationRepository) throws Exception {
        if (isAuthenticationEnabled) {
            http
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/" ,"/actuator/**","/tlogin.html").permitAll()
                            .anyRequest().authenticated())
                    .oauth2Login(oauth2 -> oauth2
                            .clientRegistrationRepository(clientRegistrationRepository))
                    .logout(l -> l.logoutSuccessHandler(new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)))
                    .csrf(c -> c.disable())
                    .exceptionHandling(exception ->
                            exception.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/tlogin.html")));
        } else {
            http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());
        }
        return http.build();
    }

    @Component
    class TenantClientRegistrationRepository implements ClientRegistrationRepository {

        private static final Map<String, ClientRegistration> clientRegistrations = new ConcurrentHashMap<>();


        @Override
        public ClientRegistration findByRegistrationId(String registrationId) {
            return clientRegistrations.computeIfAbsent(registrationId, this::buildClientRegistration);
        }

        private ClientRegistration buildClientRegistration(String tenantId) {
            return ClientRegistration.withRegistrationId(tenantId)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .scope("openid")
                    .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .userNameAttributeName(userNameAttribute)
                    .authorizationUri(frontendUri.replaceAll("\\{tenantId}", tenantId))
                    .tokenUri(baseUri.replaceAll("\\{tenantId}", tenantId) + "/token")
                    .userInfoUri(baseUri.replaceAll("\\{tenantId}", tenantId) + "/userinfo")
                    .jwkSetUri(baseUri.replaceAll("\\{tenantId}", tenantId) + "/certs")
                    .build();
        }
    }

}

