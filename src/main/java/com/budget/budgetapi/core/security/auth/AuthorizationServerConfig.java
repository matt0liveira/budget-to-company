package com.budget.budgetapi.core.security.auth;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.budget.budgetapi.domain.repository.UserRepository;

@Configuration
public class AuthorizationServerConfig {

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

    @Autowired
    private BudgetSecurityProperties budgetSecurityProperties;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authFilterChain(HttpSecurity http, JdbcOperations jdbcOperations) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> authServerConfigurer = new OAuth2AuthorizationServerConfigurer<>();

        // authServerConfigurer.authorizationEndpoint(customizer ->
        // customizer.consentPage("/oauth2/consent"));

        RequestMatcher endpointsMatcher = authServerConfigurer.getEndpointsMatcher();

        http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequest -> authorizeRequest.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authServerConfigurer);

        return http
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    ProviderSettings providerSettings() {
        return ProviderSettings
                .builder()
                .issuer(budgetSecurityProperties.getProviderUrl())
                .build();
    }

    @Bean
    RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder,
            JdbcOperations jdbcOperations) {
        return new JdbcRegisteredClientRepository(jdbcOperations);
    }

    @Bean
    OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations,
            RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    JWKSource<SecurityContext> jwkSource() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        var keyStorePass = jwtKeyStoreProperties.getPassword();
        InputStream inputStream = jwtKeyStoreProperties.getJksLocation().getInputStream();

        keyStore.load(inputStream, keyStorePass.toCharArray());

        RSAKey rsaKey = RSAKey.load(keyStore, jwtKeyStoreProperties.getAlias(),
                jwtKeyStoreProperties.getPassword().toCharArray());

        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UserRepository userRepository) {
        return context -> {
            Authentication auth = context.getPrincipal();
            if (auth.getPrincipal() instanceof User) {
                User userAuth = (User) auth.getPrincipal();

                com.budget.budgetapi.domain.model.User user = userRepository
                        .findByEmail(userAuth.getUsername())
                        .orElseThrow();

                Set<String> authorities = new HashSet<>();

                for (GrantedAuthority authority : userAuth.getAuthorities()) {
                    authorities.add(authority.getAuthority());
                }

                context.getClaims().claim("user_id", user.getId());
                context.getClaims().claim("user_full_name", user.getName());
                context.getClaims().claim("authorities", authorities);
            }
        };
    }
}
