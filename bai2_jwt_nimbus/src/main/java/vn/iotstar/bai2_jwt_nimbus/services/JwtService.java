package vn.iotstar.bai2_jwt_nimbus.services;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String extractUsername(String token) {
        try {
            return parseToken(token).getSubject();
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("JWT không hợp lệ", ex);
        }
    }

    public JWTClaimsSet parseToken(String token) {
        try {
            return SignedJWT.parse(token).getJWTClaimsSet();
        } catch (ParseException ex) {
            throw new IllegalArgumentException("JWT không hợp lệ", ex);
        }
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails);
    }

    public String generateToken(Map<String, Object> customClaims, UserDetails userDetails) {
        Date now = new Date();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(userDetails.getUsername())
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + expirationTime))
                .claim("authorities", userDetails.getAuthorities().stream().map(Object::toString).toList())
                .build();
        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder(claims);
        customClaims.forEach(claimsBuilder::claim);
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsBuilder.build());
        try {
            signedJWT.sign(new MACSigner(getSecretBytes()));
            return signedJWT.serialize();
        } catch (JOSEException ex) {
            throw new IllegalStateException("Không thể ký JWT", ex);
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return verifyToken(token) && userDetails.getUsername().equals(extractUsername(token));
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (!JWSAlgorithm.HS256.equals(signedJWT.getHeader().getAlgorithm())) return false;
            JWSVerifier verifier = new MACVerifier(getSecretBytes());
            Date expiresAt = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expiresAt != null && expiresAt.after(new Date()) && signedJWT.verify(verifier);
        } catch (ParseException | JOSEException | IllegalArgumentException ex) {
            return false;
        }
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    /** Kept as an alias for code from the first project version. */
    public long getJwtExpiration() {
        return getExpirationTime();
    }

    private byte[] getSecretBytes() {
        byte[] key;
        try {
            key = Base64.getDecoder().decode(secretKey);
        } catch (IllegalArgumentException ex) {
            key = secretKey.getBytes(StandardCharsets.UTF_8);
        }
        if (key.length < 32) {
            throw new IllegalStateException("JWT secret key phải có ít nhất 32 bytes cho HS256");
        }
        return key;
    }
}
