package com.eyeofender.utils;

import com.eyeofender.account.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenUtil {

    //1시간으로 설정
    private final long EXPIRATION_TIME;

    private final SecretKey SECRET_KEY;

    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getUsername());
        claims.put("Role", member.getRole());

        return claims;
    }

    public JwtTokenUtil(@Value("${jwt.secret}") String secretKey,
                        @Value("${jwt.expiration_time}") long expirationTime) {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        EXPIRATION_TIME = expirationTime;
        this.SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createToken(Member member) {

        return Jwts.builder()
                .issuer("Eye of ender")
                .subject(member.getUsername())
                .claims(createClaims(member))
                .signWith(SECRET_KEY, Jwts.SIG.HS512)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            log.info("서명이 유효하지 않습니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (JwtException e) {
            log.info("잘못된 JWT 입니다.");
        }

        return false;
    }

    public String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
