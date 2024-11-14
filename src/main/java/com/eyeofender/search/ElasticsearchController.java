package com.eyeofender.search;

import com.eyeofender.response.GenericResponse;
import com.eyeofender.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/es")
public class ElasticsearchController {

    private final ElasticsearchService elasticsearchService;

    public ElasticsearchController(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }

    @GetMapping("/index")
    public ResponseEntity<GenericResponse> getIndexName() {
        return ResponseUtil.buildResponse("응답 완료", HttpStatus.OK, elasticsearchService.getIndexNames());
    }
}
