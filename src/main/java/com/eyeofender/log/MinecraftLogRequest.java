package com.eyeofender.log;

public record MinecraftLogRequest(
        String indexName,
        int page,
        int size
) {
}
