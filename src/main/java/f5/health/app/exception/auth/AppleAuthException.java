package f5.health.app.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AppleAuthException extends AuthenticationException {

    public AppleAuthException(AuthErrorCode authErrorCode, Throwable cause) {
        super(authErrorCode, cause);
    }
}
