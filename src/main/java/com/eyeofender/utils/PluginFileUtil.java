package com.eyeofender.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class PluginFileUtil {

    private final Path basePath;

    public PluginFileUtil(@Value("${file.path}") String path) {
        this.basePath = Paths.get(path);
    }

    public Path saveFileWithUUID(MultipartFile file, String serverName) {
        try {
            String uuid = UUID.randomUUID().toString();
            Path directoryPath = basePath.resolve(serverName).resolve(uuid);
            if(!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
                log.info("UUID 디렉토리를 생성했습니다: {}", directoryPath);
            }
            Path filePath = directoryPath.resolve(file.getOriginalFilename());

            file.transferTo(filePath);
            log.info("파일이 저장되었습니다. {}", filePath);

            return filePath;

        } catch (IOException e) {
            log.error("파일 저장 중 오류가 발생했습니다: {}", e.getMessage());
            throw new RuntimeException("파일 저장에 실패했습니다.",e);
        }

    }
}
