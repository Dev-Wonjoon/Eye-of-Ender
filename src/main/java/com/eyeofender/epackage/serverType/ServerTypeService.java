package com.eyeofender.epackage.serverType;

import com.eyeofender.epackage.plugin.PluginPackage;
import com.eyeofender.exception.DataNotFoundException;
import com.eyeofender.epackage.plugin.PluginPackageRepository;
import com.eyeofender.response.GenericResponse;
import com.eyeofender.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServerTypeService {

    private final ServerTypeRepository serverTypeRepository;
    private final PluginPackageRepository pluginPackageRepository;

    public ServerTypeService(ServerTypeRepository serverTypeRepository, PluginPackageRepository pluginPackageRepository) {
        this.serverTypeRepository = serverTypeRepository;
        this.pluginPackageRepository = pluginPackageRepository;
    }

    public Boolean checkServerType(String serverName) {
        return serverTypeRepository.findByName(serverName).isPresent();
    }

    public ServerType getServerType(String serverName) {
        return serverTypeRepository.findByName(serverName).orElseThrow(() ->
                new DataNotFoundException("데이터가 존재하지 않습니다."));
    }

    public ResponseEntity<GenericResponse> createServerType(String serverName, String version, PluginPackage pluginPackage) {

        Optional<ServerType> _serverType = serverTypeRepository.findByName(serverName);
        if(_serverType.isPresent()) {
            return ResponseUtil.buildResponse("이미 존재하는 서버입니다.", HttpStatus.CONFLICT);
        }

        ServerType serverType = ServerType.builder()
                .name(serverName)
                .version(version)
                .build();

        if(pluginPackage != null) {
            serverType.addPackage(pluginPackage);
        }

        serverTypeRepository.save(serverType);
        return ResponseUtil.buildResponse("성공적으로 생성되었습니다.", HttpStatus.CREATED);
    }

    public ResponseEntity<GenericResponse> getLatestServerPackage(ServerType serverType) {
        return pluginPackageRepository.findTopByServerTypeOrderByCreatedAtDesc(serverType)
                .map(pluginPackage -> {
                    return ResponseUtil.buildResponse("Latest 버전 플러그인 로드 완료", HttpStatus.OK);
                })
                .orElseGet(() -> {
                    return ResponseUtil.buildResponse("서버 타입에 해당하는 패키지가 없습니다.", HttpStatus.NOT_FOUND);
                });
    }

}
