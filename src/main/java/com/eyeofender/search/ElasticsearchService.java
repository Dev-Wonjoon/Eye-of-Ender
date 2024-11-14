package com.eyeofender.search;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchService {
    private final RestClient restClient;

    public ElasticsearchService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<String> getIndexNames() {
        List<String> indexNames = new ArrayList<>();
        try {
            Request request = new Request("GET", "/_cat/indices/minecraft-*");
            request.addParameter("format", "json");
            Response response = restClient.performRequest(request);
            String responseBody = EntityUtils.toString(response.getEntity());

            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> indices = mapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>() {});

            for(Map<String, Object> index : indices) {
                indexNames.add((String) index.get("index"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexNames;
    }
}
