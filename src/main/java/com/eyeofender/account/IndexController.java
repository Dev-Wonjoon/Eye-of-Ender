package com.eyeofender.account;

import com.eyeofender.response.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IndexController {

    @GetMapping("/hello")
    public ResponseEntity<GenericResponse> index() {
        GenericResponse response = new GenericResponse("hello 인증 완료!", 200);
        return ResponseEntity.ok().body(response);
    }
}
