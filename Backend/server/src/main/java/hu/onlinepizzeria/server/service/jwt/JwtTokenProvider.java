package hu.onlinepizzeria.server.service.jwt;

import hu.onlinepizzeria.server.service.AuthenticationService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
	
	@Autowired
    JwtProperties jwtProperties;

    @Autowired
    private AuthenticationService userDetailsService;
    
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecretKey().getBytes());
    }

    public String createToken(String username, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getValidityInMs());

        return Jwts.builder()//
            .setClaims(claims)//
            .setIssuedAt(now)//
            .setExpiration(validity)//
            .signWith(SignatureAlgorithm.HS256, secretKey)//
            .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public boolean isAdmin(String token) {
        return getAuthentication(token)
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    public boolean isDelivery(String token) {
        return getAuthentication(token)
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_DELIVERY"));
    }
    public boolean isKitchen(String token) {
        return getAuthentication(token)
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_KITCHEN"));
    }
    public boolean isUser(String token) {
        return getAuthentication(token)
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_USER"));
        
    }
    public boolean isManager(String token) {
        return getAuthentication(token)
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }

    public String getUsername(String token) throws SignatureException{
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

}
