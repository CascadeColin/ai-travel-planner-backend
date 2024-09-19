package travel.planner.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import travel.planner.models.Planner;

import java.io.IOException;

public class JwtRequestFilter extends BasicAuthenticationFilter {
    private final JwtConverter converter;

    public JwtRequestFilter(AuthenticationManager authenticationManager, JwtConverter converter) {
        super(authenticationManager);
        this.converter = converter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            Planner planner = converter.getUserFromToken(authorization);
            if (planner == null) {
                response.setStatus(403);
            } else {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        planner, null, planner.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        chain.doFilter(request, response);
    }
}
