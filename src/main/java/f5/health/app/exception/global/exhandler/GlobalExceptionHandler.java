package f5.health.app.exception.global.exhandler;

import f5.health.app.exception.response.CustomFieldError;
import f5.health.app.exception.response.FieldErrorsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public FieldErrorsResult MethodArgumentNotValidExHandler(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidExHandler", ex);
        List<CustomFieldError> customFieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new CustomFieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return new FieldErrorsResult(customFieldErrors);
    }

}
