package travel.planner.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;
import travel.planner.models.Planner;


import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtConverter {
    private final SecretKey key = Jwts.SIG.HS256.key().build();
    private final String ISSUER = "travel-planner";

    public String getTokenFromPlanner(Planner planner) {
        // TODO: lower in the future, keeping it 20 minutes for presentation purposes
        int EXPIRATION_MINUTES = 20;
        return Jwts.builder()
                .issuer(ISSUER)
                .subject(planner.getUsername())
                .claim("plannerId", planner.getPlannerId())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MINUTES * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public Planner getUserFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parser()
                    .requireIssuer(ISSUER)
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token.substring(7));
            int plannerId = jws.getPayload().get("plannerId", Integer.class);
            String username = jws.getPayload().getSubject();
            return new Planner(plannerId, username, "", true, "name", 0, 0);
        }
        catch (JwtException e) {
            System.out.println(e); //TODO: handle this in GlobalErrorHandler
        }

        return null;
    }
}
