package com.replix.office.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    //private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    //@Value("${app.jwtSecret}")
    private String jwtSecret="xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

    //@Value("${app.jwtExpirationMs}")//TODO:set fromproperty file
    private int jwtExpirationMs=300000;

    public String generateJwtToken(Authentication authentication) {

        //UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject("yasitha.dev@gmail.com")
                .setIssuedAt(new Date())//replace util date with date time API
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(this.key(), SignatureAlgorithm.HS256)
                .compact();
        //return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
      /*  return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
       */
        return "yasitha.dev@gmail.com";
    }

    public boolean validateJwtToken(String authToken) {
       try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
           System.out.println("Invalid JWT token: {}"+ e.getMessage());//TODO:remove after enabling logger
           // logger.error("Invalid JWT token: {}", e.getMessage());//TODO:enable logger
        } catch (ExpiredJwtException e) {
           System.out.println("Invalid JWT token: {}"+ e.getMessage());//TODO:remove after enabling logger
            //logger.error("JWT token is expired: {}", e.getMessage());//TODO:enable logger
        } catch (UnsupportedJwtException e) {
           System.out.println("JWT token is unsupported: {}"+ e.getMessage());//TODO:remove after enabling logger
            //logger.error("JWT token is unsupported: {}", e.getMessage());//TODO:enable logger
        } catch (IllegalArgumentException e) {
           System.out.println("WT claims string is empty: {}"+ e.getMessage());//TODO:remove after enabling logger
            //logger.error("JWT claims string is empty: {}", e.getMessage());//TODO:enable logger
        }
        return false;
    }
}
