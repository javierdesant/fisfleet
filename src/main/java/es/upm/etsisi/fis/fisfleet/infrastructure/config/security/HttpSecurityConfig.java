package es.upm.etsisi.fis.fisfleet.infrastructure.config.security;

import es.upm.etsisi.fis.fisfleet.infrastructure.config.security.filter.JwtAuthenticationFilter;
import es.upm.etsisi.fis.fisfleet.utils.RolePermission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    private final SessionCreationPolicy sessionPolicy = SessionCreationPolicy.STATELESS;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider daoAuthenticationProvider,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessMagConfig ->
                        sessMagConfig.sessionCreationPolicy(this.sessionPolicy))
                .authenticationProvider(daoAuthenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(this::buildRequestMatchers);

        return http.build();
    }

    private void buildRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        this.configurePublicEndpoints(authReqConfig);
        this.configurePlayerEndpoints(authReqConfig);
        this.configureAdminEndpoints(authReqConfig);

        authReqConfig.anyRequest().authenticated();
    }

    private void configurePublicEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        // TODO: decide which public/auth methods are necessary
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate-token").permitAll();  // TEST: is this a test?
        authReqConfig.requestMatchers(HttpMethod.POST, "/api/users/register").hasAuthority(RolePermission.REGISTER.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/api/users/login").hasAuthority(RolePermission.LOGIN.name());
    }


    private void configurePlayerEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/users/profile").authenticated();
        authReqConfig.requestMatchers(HttpMethod.PUT, "/api/users/profile").authenticated();

        authReqConfig.requestMatchers(HttpMethod.POST, "/api/matches")
                .hasAuthority(RolePermission.PLAY_GAME.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/api/matches")
                .hasAuthority(RolePermission.PLAY_GAME.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/matches/{matchId}")
                .hasAuthority(RolePermission.PLAY_GAME.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/api/matches/{matchId}/attack")
                .hasAuthority(RolePermission.PLAY_GAME.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/api/matches/{matchId}/abilities")
                .hasAuthority(RolePermission.PLAY_GAME.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/matches/{matchId}/board")
                .hasAuthority(RolePermission.PLAY_GAME.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/api/matches/{matchId}/finish")
                .hasAuthority(RolePermission.PLAY_GAME.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/matches/{matchId}/replay")
                .hasAuthority(RolePermission.PLAY_GAME.name());

        // Puntuaciones accesibles por VIEW_SCOREBOARD
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/scores/top10")
                .hasAuthority(RolePermission.VIEW_SCOREBOARD.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/scores/user/{userId}")
                .hasAuthority(RolePermission.VIEW_SCOREBOARD.name());
    }

    private void configureAdminEndpoints(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/scores")
                .hasAuthority(RolePermission.VIEW_ALL_SCORES.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/admin/matches")
                .hasAuthority(RolePermission.VIEW_ALL_SCORES.name());
        authReqConfig.requestMatchers(HttpMethod.POST, "/api/admin/machines")
                .hasAuthority(RolePermission.CONFIGURE_SYSTEM.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/admin/machines")
                .hasAuthority(RolePermission.CONFIGURE_SYSTEM.name());
        authReqConfig.requestMatchers(HttpMethod.GET, "/api/admin/machines/strategies")
                .hasAuthority(RolePermission.ACCESS_ADMIN_PANEL.name());
    }
}
