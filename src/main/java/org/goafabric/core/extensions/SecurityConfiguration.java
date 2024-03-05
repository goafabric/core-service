package org.goafabric.core.extensions;

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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class SecurityConfiguration {
    @Value("${security.authentication.enabled}") private Boolean isAuthenticationEnabled;

    @Value("${spring.security.oauth2.base-uri}") private String baseUri;
    @Value("${spring.security.oauth2.authorization-uri}") private String authorizationUri;
    @Value("${spring.security.oauth2.logout-uri:}") private String logoutUri;
    @Value("${spring.security.oauth2.prefix:}") private String prefix;

    @Value("${spring.security.oauth2.client-id}") private String clientId;
    @Value("${spring.security.oauth2.client-secret}") private String clientSecret;
    @Value("${spring.security.oauth2.user-name-attribute:}") private String userNameAttribute;

    private static final String TENANT_ID = "0"; //we are always using a fixed Registration ID here

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        if (isAuthenticationEnabled) {
            var clientRegistrationRepository = new TenantClientRegistrationRepository();
            var logoutHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
            logoutHandler.setPostLogoutRedirectUri("{baseUrl}/oauth2/authorization/" + TENANT_ID); //yeah that's right, we need baseUrl here, because it's an absolute url and below its a relative url - WTF
            http
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(new MvcRequestMatcher(introspector, "/"), new MvcRequestMatcher(introspector, "actuator/**"), new MvcRequestMatcher(introspector, "/login.html")).permitAll()
                            .anyRequest().authenticated())
                    .oauth2Login(oauth2 -> oauth2
                            .clientRegistrationRepository(clientRegistrationRepository)
                            //.defaultSuccessUrl("/frontend/",true)
                    )
                    .logout(l -> l.logoutSuccessHandler(logoutHandler))
                    .csrf(c -> c.disable())
                    .exceptionHandling(exception ->
                            exception.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/oauth2/authorization/" + TENANT_ID)));
        } else {
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).csrf(csrf -> csrf.disable());
        }
        return http.build();
    }

    class TenantClientRegistrationRepository implements ClientRegistrationRepository {

        private static final Map<String, ClientRegistration> clientRegistrations = new ConcurrentHashMap<>();
        
        @Override
        public ClientRegistration findByRegistrationId(String registrationId) {
            return clientRegistrations.computeIfAbsent(TENANT_ID, this::buildClientRegistration);
        }

        private ClientRegistration buildClientRegistration(String tenantId) {
            var providerDetails = new HashMap<String, Object>();
            providerDetails.put("end_session_endpoint", !logoutUri.equals("") ? logoutUri.replaceAll("\\{tenantId}", tenantId) : null);

            return ClientRegistration.withRegistrationId(tenantId)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .scope("openid")
                    .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .userNameAttributeName(userNameAttribute)
                    .authorizationUri(authorizationUri.replaceAll("\\{tenantId}", tenantId))
                    .tokenUri(baseUri.replaceAll("\\{tenantId}", tenantId) + "/token")
                    .userInfoUri(baseUri.replaceAll("\\{tenantId}", tenantId) + "/userinfo")
                    .jwkSetUri(baseUri.replaceAll("\\{tenantId}", tenantId) + "/certs")
                    .providerConfigurationMetadata(providerDetails)
                    .build();
        }
    }

}

