package com.eyeofender.account;

import com.eyeofender.account.request.MemberRequest;
import com.eyeofender.response.GenericResponse;
import com.eyeofender.utils.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class MemberController {

    private final MemberService memberService;

    private final JwtTokenUtil jwtTokenUtil;

    public MemberController(MemberService memberService, JwtTokenUtil jwtTokenUtil) {
        this.memberService = memberService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> signup(@Valid @RequestBody MemberRequest request) {
        GenericResponse response;

        if(!memberService.duplicateUsername(request.username())) {
            memberService.signupUser(request.username(), request.password());
            response = new GenericResponse("회원가입 완료", 200);
        } else {
            response = new GenericResponse("이미 가입 되어있는 회원입니다.", 409);
            return ResponseEntity.status(409).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<GenericResponse> signin(@Valid @RequestBody MemberRequest request) {
        Member member = memberService.getMember(request.username());
        if(!memberService.checkPassword(request.password(), member.getPassword())) {
            return ResponseEntity.badRequest().body(
                    new GenericResponse("아이디 또는 패스워드가 맞지 않습니다", 401)
            );
        }
        ResponseCookie jwtCookie = ResponseCookie.from(
                "accessToken",
                jwtTokenUtil.createToken(member))
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new GenericResponse("로그인 되었습니다", 200));
    }

    @PostMapping("/signout")
    public ResponseEntity<GenericResponse> signout(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        GenericResponse genericResponse = new GenericResponse("로그아웃 성공", 200);
        return ResponseEntity.ok(genericResponse);

    }
}
