package com.eyeofender.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record MemberRequest(
        @NotBlank(message = "ID는 필수 항목입니다.")
        @Size(min = 4, max = 32, message = "최소 4글자, 최대 32글자입니다.")
        String username,
        @NotBlank(message = "Password는 필수 항목입니다.")
        @Size(min = 4, max = 255, message = "최소 4글자, 최대 255글자입니다.")
        String password
) {
}
