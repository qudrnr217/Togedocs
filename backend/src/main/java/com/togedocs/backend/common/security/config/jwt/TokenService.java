package com.togedocs.backend.common.security.config.jwt;

import com.togedocs.backend.api.dto.Token;

import com.togedocs.backend.api.dto.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;


@Service
//@ConfigurationProperties("jwt")
public class TokenService{

    private String secretKey = JwtProperties.SECRET;


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


//    @Override
    public Token generateToken(String email, String uuid, String name, int imgNo) {
        long tokenPeriod = 1000L * 60L * 30L;
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("uuid", uuid);
        claims.put("name",name);
        claims.put("imgNo",imgNo);

        Date now = new Date();
        Token token = new Token(
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + tokenPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + refreshPeriod))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact());
        return token;
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public String getUid(String token) {
        System.out.println(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody());
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
