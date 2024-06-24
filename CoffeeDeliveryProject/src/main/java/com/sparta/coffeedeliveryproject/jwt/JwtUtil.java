package com.sparta.coffeedeliveryproject.jwt;

import com.sparta.coffeedeliveryproject.entity.User;
import com.sparta.coffeedeliveryproject.entity.UserRole;
import com.sparta.coffeedeliveryproject.enums.UserStatusEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // AccessToken 만료시간 60분
    private final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L;
    // RefreshToken 만료시간 14일
    private final long REFRESHTOKEN_TIME = 14 * 24 * 60 * 60 * 1000L;

    // Base64 Encode 한 SecretKey
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    // 딱 한번만 받아오면 되는 값을 사용 할 때마다 새로 요청하는 실수를 방지하기 위한
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createAccessToken(String userName, UserStatusEnum userStatus) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(userName) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, userStatus) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String createRefreshToken(User user) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setExpiration(new Date(date.getTime() + REFRESHTOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();

    }

    public void addJwtToHeader(String token, HttpServletResponse res) {

        res.addHeader(AUTHORIZATION_HEADER, token);
    }

    // AccessToken을 header에서 가져와서 반환하는 메서드
    public String getAccessTokenFromHeader(HttpServletRequest request) {
        // header에서 AccessToken을 가져온다.
        String accessToken = request.getHeader(AUTHORIZATION_HEADER);
        // 공백(null)인지 && BEARER로 시작을 하는지 확인
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
            // 둘 다 만족할 경우 BEARER 뒤에 공백 길이만큼 잘라내고 순수한 토큰 값만을 리턴
            return accessToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
