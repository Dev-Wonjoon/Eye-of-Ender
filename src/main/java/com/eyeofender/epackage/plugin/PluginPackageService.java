package com.eyeofender.epackage.plugin;

import com.eyeofender.epackage.serverType.ServerType;
import com.eyeofender.exception.DataNotFoundException;
import com.eyeofender.utils.PluginFileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class PluginPackageService {
    private final PluginFileUtil pluginFileUtil;
    private final PluginPackageRepository pluginPackageRepository;

    public PluginPackageService(PluginFileUtil pluginFileUtil, PluginPackageRepository pluginPackageRepository) {
        this.pluginFileUtil = pluginFileUtil;
        this.pluginPackageRepository = pluginPackageRepository;
    }

    public PluginPackage savePluginPackage(MultipartFile file, ServerType serverType) {
        Path savedFilePath = pluginFileUtil.saveFileWithUUID(file, serverType.getName());

        PluginPackage pluginPackage = PluginPackage.builder()
                .name(file.getOriginalFilename())
                .path(savedFilePath.toString())
                .createdAt(LocalDateTime.now())
                .serverType(serverType)
                .build();

        return pluginPackageRepository.save(pluginPackage);
    }

    public PluginPackage getPluginPackage(String packageName) {

        return pluginPackageRepository.findByName(packageName).orElseThrow(() -> {
            return new DataNotFoundException("데이터가 존재하지 않습니다.");
        });
    }
}
