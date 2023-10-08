package uk.co.topfieldconsultancy.stem.application;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.topfieldconsultancy.stem.domain.StemConfig;
import uk.co.topfieldconsultancy.stem.domain.UserRepository;

import javax.crypto.SecretKey;

@Component
public class JWTApplication {

    private static SecretKey key;
    @Autowired
    StemConfig stemConfig;
    @Autowired
    UserRepository userRepository;


    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(stemConfig.getSigningKey()));
    }

    public String createJWT(String[] roles, Long userId) {

        JwtBuilder jwtBuilder = Jwts.builder()
                .header()
                .keyId(String.valueOf(userId))
                .and()
                .subject(String.valueOf(userId))
                .claim("roles", String.join(",", roles))
                .signWith(key);

        return jwtBuilder.compact();
    }

    private boolean isAuthenticated(String jwt) {

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parse(jwt);
            return true;
        } catch (JwtException ex) {   // (5)
            return false;
        }
    }

    public void checkRoles(String[] roles) {

    }

    private Long getUserId(String jwt) {
        try {
            String userId = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
            return Long.parseLong(userId);
        } catch (JwtException ex) {   // (5)
            throw new IllegalArgumentException("UserId could not be read from JWT");
        }
    }

    public Long verifyAndExtractUserId(String jwt) {
        this.isAuthenticated(jwt);
        return this.getUserId(jwt);

    }
}
