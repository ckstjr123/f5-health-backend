package f5.health.app.exception.member;

import f5.health.app.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum MemberErrorCode implements ErrorCode {

//    INVALID_ALCOHOL_DRINKING_INFO("음주량 정보가 올바르지 않습니다."),
    NOT_FOUND_MEMBER("존재하지 않는 회원입니다."),
    ALREADY_JOINED_MEMBER("이미 가입된 회원입니다.");

    private final String code;
    private final String message;

    MemberErrorCode(String message) {
        this.code = this.name();
        this.message = message;
    }
}
