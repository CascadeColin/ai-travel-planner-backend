package travel.planner.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class SecurityConfig {
    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests(authorize -> authorize
// TODO: reduce access once it is established who can access what
                .requestMatchers(HttpMethod.GET, "/trip").permitAll()
                .requestMatchers(HttpMethod.GET, "/trip/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/trip/planner/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/trip").permitAll()
                .requestMatchers(HttpMethod.POST, "/trip/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/trip").permitAll()
                .requestMatchers(HttpMethod.PUT, "/trip/*").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/trip").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/trip/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/config").permitAll()
                .requestMatchers(HttpMethod.GET, "/config/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/config").permitAll()
                .requestMatchers(HttpMethod.POST, "/config/*").permitAll()
                .requestMatchers(HttpMethod.PUT, "/config").permitAll()
                .requestMatchers(HttpMethod.PUT, "/config/*").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/config").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/config/*").permitAll()
                .requestMatchers("/authenticate").permitAll() // needed so users can reach AuthController
                .requestMatchers("/refresh_token").authenticated()
                .requestMatchers("/create_account").permitAll()
                .requestMatchers("/**").denyAll()
        );

        http.addFilter(new JwtRequestFilter(manager(config), converter));

        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // 1. Stateless session management

        return http.build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
