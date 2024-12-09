package me.cher1shrxd.firstspringboot.global.security.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.domain.auth.enums.UserRole;
import me.cher1shrxd.firstspringboot.domain.auth.repository.RefreshTokenRepository;
import me.cher1shrxd.firstspringboot.domain.user.entity.UserEntity;
import me.cher1shrxd.firstspringboot.domain.user.repository.UserRepository;
import me.cher1shrxd.firstspringboot.global.exception.CustomErrorCode;
import me.cher1shrxd.firstspringboot.global.exception.CustomException;
import me.cher1shrxd.firstspringboot.global.security.details.CustomUserDetails;
import me.cher1shrxd.firstspringboot.global.security.jwt.config.JwtProperties;
import me.cher1shrxd.firstspringboot.global.security.jwt.dto.JwtResponse;
import me.cher1shrxd.firstspringboot.global.security.jwt.enums.JwtType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private SecretKey key;

    @PostConstruct
    protected void init() {
        System.out.println("Loaded SECRET_KEY: " + jwtProperties.getSecretKey());
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecretKey()));
        System.out.println("key: " + key);
    }

    public JwtResponse generateToken(String username, UserRole role) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .header()
                .type(JwtType.ACCESS.name())
                .and()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.getExpiration()))
                .signWith(key)
                .compact();

        String refreshToken = Jwts.builder()
                .header()
                .type(JwtType.REFRESH.name())
                .and()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.getRefreshExpiration()))
                .signWith(key)
                .compact();

        refreshTokenRepository.save(username, refreshToken);

        return new JwtResponse(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        if (getType(token) != JwtType.ACCESS) {
            throw new CustomException(CustomErrorCode.INVALID_TOKEN_TYPE);
        }

        UserEntity user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));

        UserDetails details = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
    }

    public String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new CustomException(CustomErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(CustomErrorCode.UNSUPPORTED_JWT_TOKEN);
        } catch (MalformedJwtException e) {
            throw new CustomException(CustomErrorCode.MALFORMED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(CustomErrorCode.INVALID_JWT_TOKEN);
        }
    }

    public JwtType getType(String token) {
        return JwtType.valueOf(Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getHeader().getType()
        );
    }

}
