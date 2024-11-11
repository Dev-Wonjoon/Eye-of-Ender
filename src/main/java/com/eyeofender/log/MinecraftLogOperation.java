package com.eyeofender.log;


import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinecraftLogOperation {

    private final ElasticsearchTemplate elasticsearchTemplate;

    public MinecraftLogOperation(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    public SearchHits<MinecraftLog> searchByMessageContent(String indexName, int page, int size, Object[] searchAfterValues) {


        NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
                .withQuery(Query.findAll())
                .withSourceFilter(new FetchSourceFilter(new String[]{"message_content"}, null))
                .withPageable(PageRequest.of(page, size))
                .withSort(Sort.by(Sort.Order.asc("@timestamp"), Sort.Order.asc("_id")));

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
