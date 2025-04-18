package f5.health.app.jwt;

import f5.health.app.exception.auth.AuthErrorCode;
import f5.health.app.exception.auth.AuthenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    public static final long ACCESS_TOKEN_EXPIRED_MS = Duration.ofMinutes(3).toMillis();
    public static final long REFRESH_TOKEN_EXPIRED_MS = Duration.ofMinutes(10).toMillis();
    public static final String JWT_EXCEPTION_ATTRIBUTE = "JWT_EXCEPTION";
    private final SecretKey secretKey;

    public JwtProvider(@Value("${spring.jwt.secret}") String secret) {
        // HS256: 양방향 대칭키 암호화 알고리즘
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }


    //로그인 성공 시 토큰 발급 역할
    public String generateJwt(Long memberId, String username, String role, Long expiredMs) {
        return Jwts.builder()
                .subject(memberId.toString())
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시각
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(this.secretKey)
                .compact();
    }

    public Claims extractVaildClaims(String token) {
        try {
            return Jwts.parser().verifyWith(this.secretKey).build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException ex) {
            throw new AuthenticationException(AuthErrorCode.SIGNATURE_JWT, ex);
        } catch (MalformedJwtException ex) {
            throw new AuthenticationException(AuthErrorCode.MALFORMED_JWT, ex);
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationException(AuthErrorCode.EXPIRED_JWT, ex);
        } catch (UnsupportedJwtException ex) {
            throw new AuthenticationException(AuthErrorCode.UNSUPPORTED_JWT, ex);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new AuthenticationException(AuthErrorCode.INVALID_JWT, ex);
        }
    }

/*    public boolean isExpired(String token) {
        try {
            Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(token);
            return false;
        } catch (ExpiredJwtException ex) {
            return true;
        } catch (SignatureException ex) {
            throw new AuthenticationException(AuthExceptionType.SIGNATURE_JWT, ex);
        } catch (MalformedJwtException ex) {
            throw new AuthenticationException(AuthExceptionType.MALFORMED_JWT, ex);
        } catch (UnsupportedJwtException ex) {
            throw new AuthenticationException(AuthExceptionType.UNSUPPORTED_JWT, ex);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new AuthenticationException(AuthExceptionType.INVALID_JWT, ex);
        }
    }*/

}