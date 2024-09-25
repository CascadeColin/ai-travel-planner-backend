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
                .requestMatchers("/authenticate").permitAll() // needed so users can reach AuthController
                .requestMatchers("/refresh_token").authenticated()
                // TODO: implement account creation as stretch goal
                .requestMatchers("/create_account").permitAll()
//                .requestMatchers(HttpMethod.GET,
//                        "/order").permitAll()
//                .requestMatchers(HttpMethod.GET,
//                        "/sighting", "/sighting/*").permitAll()
//                .requestMatchers(HttpMethod.POST,
//                        "/sighting").hasAnyAuthority("USER", "ADMIN")
//                .requestMatchers(HttpMethod.PUT,
//                        "/sighting/*").hasAnyAuthority("USER", "ADMIN")
//                .requestMatchers(HttpMethod.DELETE,
//                        "/sighting/*").hasAnyAuthority("ADMIN")
                // TODO: add my own endpoints here, above is for syntax demonstration
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
