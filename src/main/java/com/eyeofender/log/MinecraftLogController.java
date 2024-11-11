package com.eyeofender.log;

import com.eyeofender.response.GenericResponse;
import com.eyeofender.utils.ResponseUtil;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log/minecraft")
public class MinecraftLogController {

    private final MinecraftLogOperation minecraftLogOperation;

    public MinecraftLogController(MinecraftLogOperation minecraftLogOperation) {
        this.minecraftLogOperation = minecraftLogOperation;
    }

    @GetMapping("/server")
    public ResponseEntity<GenericResponse> getServerLog(
            @RequestParam String indexName,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) List<Object> searchAfterValues
            ) {
        Object[] searchAfterArray = searchAfterValues != null ? searchAfterValues.toArray() : null;

        SearchHits<MinecraftLog> searchHits = minecraftLogOperation.searchByMessageContent(indexName, page, size, searchAfterArray);



        return ResponseUtil.buildResponse(
                "응답 완료",
                HttpStatus.OK,
                searchHits.getSearchHits()
        );

    }
}
