package com.replix.office.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.replix.office.models.User;
import com.replix.office.security.UserDetailsImpl;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl UserDetails = (UserDetailsImpl) authentication.getPrincipal();
        logger.debug("userPrincipal.getEmail() " + UserDetails.getEmail());
        return Jwts.builder()
                .setSubject(UserDetails.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(this.key(), SignatureAlgorithm.HS256)
                .claim("Authorities",UserDetails.getAuthoritiesJsonString())
                .compact();
        //return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    }

    private Key key() {
        logger.debug("jwtSecret " +jwtSecret);
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        logger.debug(" jwt body" + Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody());
      return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
       // return "yasitha.dev@gmail.com";
    }
    public UserDetails getUserDetailsFromJwtToken(String jwt) {
        Claims jwtBodyclaims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(jwt).getBody();
        System.out.println("jwtBodyclaims " + jwtBodyclaims);//TODO:Add logs
        String jsonAuthorities =(String)jwtBodyclaims.get("Authorities");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<String> AuthoritiesList = mapper.readValue(jsonAuthorities, new TypeReference<List<String>>() {});
            System.out.println("Authorities array " + AuthoritiesList);//TODO:Add logs
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            for(String Auth: AuthoritiesList) authorities.add(new SimpleGrantedAuthority(Auth));

            return new UserDetailsImpl(
                    1,
                    jwtBodyclaims.getSubject(),
                    jwtBodyclaims.getSubject(),
                    null,
                    authorities,
                    jsonAuthorities,
                    true);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean validateJwtToken(String authToken) {
       try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
           logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
           logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
           logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
           logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
