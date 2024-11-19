package com.eyeofender.log;

import com.eyeofender.response.GenericResponse;
import com.eyeofender.search.ElasticsearchService;
import com.eyeofender.utils.ResponseUtil;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MinecraftLogController {

    private final MinecraftLogService minecraftLogService;
    private final ElasticsearchService elasticsearchService;

    public MinecraftLogController(MinecraftLogService minecraftLogOperation, ElasticsearchService elasticsearchService) {
        this.minecraftLogService = minecraftLogOperation;
        this.elasticsearchService = elasticsearchService;
    }

    @GetMapping("/search")
    public ResponseEntity<GenericResponse> getServerByLog(
            @RequestParam String indexName,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) List<Object> searchAfterValues
            ) {
        Object[] searchAfterArray = searchAfterValues != null ? searchAfterValues.toArray() : null;

        SearchHits<MinecraftLog> searchHits = minecraftLogService.searchByMessageContent(indexName, page, size, searchAfterArray);


        return ResponseUtil.buildResponse(
                "응답 완료",
                HttpStatus.OK,
                searchHits.getSearchHits()
        );
    }
    @GetMapping("/search/index")
    public ResponseEntity<GenericResponse> getAllIndexNames() {
        List<String> indexNames = elasticsearchService.getIndexNames();

        return ResponseUtil.buildResponse("인덱스 조회 완료", HttpStatus.OK, indexNames);
    }
}
