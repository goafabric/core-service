package org.goafabric.core.data.crossfunctional;

import io.micrometer.observation.ObservationPredicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if (isAuthenticationEnabled) {
            http
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/" ,"/actuator/**", "/welcome/**").permitAll()
                            .anyRequest().authenticated())
                    .oauth2Login(Customizer.withDefaults());
        }
        return http.build();
    }


    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(@Value("${multi-tenancy.tenants}") String tenants) {
        return new InMemoryClientRegistrationRepository(
                Arrays.stream(tenants.split(",")).map(this::createClientRegistration).toList());
    }

    private ClientRegistration createClientRegistration(String tenantId) {
        var fix = prefix.replaceAll("\\{tenantId}", tenantId);
        return ClientRegistration.withRegistrationId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope("openid")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .userNameAttributeName(userNameAttribute)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .authorizationUri(frontendUri + fix + "/auth")
                .tokenUri(baseUri + fix + "/token")
                .userInfoUri(baseUri + fix + "/userinfo")
                .jwkSetUri(baseUri + fix + "/certs")
                .build();
    }


    @Bean
    ObservationPredicate disableHttpServerObservationsFromName() {
        return (name, context) -> !name.startsWith("spring.security.");
    }

    /*
    @Bean
    GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            final Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                mappedAuthorities.add(authority); //add original authority if needed
                if (authority instanceof OidcUserAuthority oidcAuth) {
                    var context = SecurityContextHolder.getContext();
                    List<String> roles = (List<String>) oidcAuth.getAttributes().get("roles");
                    //add role from oidc attributes so that f*** spring can understand it and also add f*** ROLE_ prefix for f*** spring
                    roles.forEach(role -> mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
                }
            });

            return mappedAuthorities;
        };
    }

     */

}

