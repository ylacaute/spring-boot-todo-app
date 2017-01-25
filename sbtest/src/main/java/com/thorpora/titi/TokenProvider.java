package com.thorpora.titi;

import com.thorpora.module.core.log.ColoredStatus;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.thorpora.module.core.log.ColoredStatus.Status.INIT;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    //@Inject
    //private JHipsterProperties jHipsterProperties;

    //jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
    String secret = "my-secret-token-to-change-in-production";

    //jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
    int tokenValidityInSeconds = 86400;

    //jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    int tokenValidityInSecondsForRememberMe = 3600;
    /*

    jhipster:
    security:
        authentication:
            jwt:
                secret: my-secret-token-to-change-in-production
                # Token is valid 24 hours
                tokenValidityInSeconds: 86400

     */
    public TokenProvider() {
        ColoredStatus.getText(Status.INIT, "Using TokenProvider configuration");
    }

    @PostConstruct
    public void init() {

        this.secretKey = secret;
        this.tokenValidityInMilliseconds = 1000 * tokenValidityInSeconds;
        this.tokenValidityInMillisecondsForRememberMe = 1000 * tokenValidityInSecondsForRememberMe;
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }
}
