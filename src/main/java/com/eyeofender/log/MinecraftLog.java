package com.eyeofender.log;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Document(createIndex = false, indexName = "minecraft-*")
@NoArgsConstructor
public class MinecraftLog {

    @Id
    private String id;

    @Field(type = FieldType.Date, name = "@timestamp")
    private Instant timestamp;

    @Field(type = FieldType.Text)
    private String message;

    @Field(type = FieldType.Object)
    private String log;

    @Field(type = FieldType.Keyword)
    private String serverType;

    @Field(type = FieldType.Keyword)
    private String server_name;

    @Field(type = FieldType.Keyword)
    private String thread;

    @Field(type = FieldType.Keyword)
    private String log_level;

    @Field(type = FieldType.Text)
    private String message_content;

    @Builder
    public MinecraftLog(Instant timestamp, String log, String message, String serverType, String server_name, String thread, String log_level, String message_content) {
        this.timestamp = timestamp;
        this.log = log;
        this.message = message;
        this.serverType = serverType;
        this.server_name = server_name;
        this.thread = thread;
        this.log_level = log_level;
        this.message_content = message_content;
    }
}
