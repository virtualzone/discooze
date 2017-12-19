package net.weweave.discooze.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(this.secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return this.getClaims(token).getSubject();
    }

    public String createToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }
}
