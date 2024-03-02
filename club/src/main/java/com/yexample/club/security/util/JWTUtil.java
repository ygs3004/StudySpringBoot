package com.yexample.club.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {

    private String secretKey = "SECRET_KEY_FOR_PROJECT_HMAC_SHA_256";
    private SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // 1달
    private long expire = 60 * 24 * 30;

    public String generateToken(String content) {
        // Key 길이에 따라 Hmac 알고리즘이 결정됨
        log.info("key Algorithm: " + key.getAlgorithm());
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .claim("sub", content)
                .signWith(key)
                .compact();
    }

    public String validateAndExtract(String tokenStr) {

        String contentValue = null;

        Jws<Claims> jws =Jwts.parser().verifyWith(key).build().parseSignedClaims(tokenStr);
        log.info(jws);
        log.info(jws.getPayload().getClass());
        Claims claims = jws.getPayload();
        log.info("subject: " + claims.getSubject());
        contentValue = claims.getSubject();

        return contentValue;
    }

}
