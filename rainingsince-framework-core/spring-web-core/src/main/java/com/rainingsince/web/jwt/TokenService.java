package com.rainingsince.web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class TokenService {
    private String key;
    private String profiles;

    public TokenService(String key, String author) {
        this.key = key;
        this.profiles = author;
    }

    public String createToken(String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setAudience(subject)
                .claim("profiles", this.profiles)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    public Claims validateToken(String jwtStr) {
        Claims claims;
        try {
            claims = parseJWT(jwtStr);
            if (claims == null) return null;
            if (this.profiles.equals(claims.get("profiles", String.class))) {
                return claims;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(this.key);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    private Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

}
