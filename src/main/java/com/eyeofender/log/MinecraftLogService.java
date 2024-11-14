package com.eyeofender.log;


import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MinecraftLogService {

    private final ElasticsearchTemplate elasticsearchTemplate;

    public MinecraftLogService(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public SearchHits<MinecraftLog> searchByMessageContent(String indexName, int page, int size, Object[] searchAfterValues) {


        NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
                .withQuery(QueryBuilders.matchAll().build()._toQuery())
                .withPageable(PageRequest.of(page, size))
                .withSort(Sort.by(Sort.Order.desc("timestamp")));

        if(searchAfterValues != null) {
            queryBuilder.withSearchAfter(List.of(searchAfterValues));
        }

        NativeQuery query = queryBuilder.build();

        return elasticsearchTemplate.search(
            query,
            MinecraftLog.class,
            IndexCoordinates.of(indexName));
    }

    public Object[] getSearchAfterValues(SearchHits<MinecraftLog> searchHits) {
        if(searchHits.isEmpty()) {
            return null;
        }

        return searchHits.getSearchHit(searchHits.getSearchHits().size()-1).getSortValues().toArray();
    }
}
