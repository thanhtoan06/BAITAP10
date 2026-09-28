package vn.iotstar.bai1_jwt_basic.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret-key}") private String secretKey;
    @Value("${jwt.expiration}") private long jwtExpiration;

    public String extractUsername(String token) { return extractClaim(token, Claims::getSubject); }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }
    public String generateToken(UserDetails userDetails) { return generateToken(Map.of(), userDetails); }
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date now = new Date();
        return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername()).issuedAt(now)
                .expiration(new Date(now.getTime() + jwtExpiration)).signWith(getSignInKey()).compact();
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) { return extractExpiration(token).before(new Date()); }
    public Date extractExpiration(String token) { return extractClaim(token, Claims::getExpiration); }
    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }
    public SecretKey getSignInKey() { return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)); }
    public long getJwtExpiration() { return jwtExpiration; }
}
