package com.eyeofender.epackage.plugin;

import com.eyeofender.epackage.serverType.ServerTypeService;
import com.eyeofender.epackage.serverType.ServerType;
import com.eyeofender.response.GenericResponse;
import com.eyeofender.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/package/plugin")
public class PluginManagerController {

    private final ServerTypeService serverTypeService;
    private final PluginPackageService pluginPackageService;

    public PluginManagerController(ServerTypeService pluginManagerService, PluginPackageService pluginPackageService) {
        this.serverTypeService = pluginManagerService;
        this.pluginPackageService = pluginPackageService;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> createPluginPackage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("version") String version,
            @RequestParam("serverName") String serverName) {
        ServerType serverType = serverTypeService.getServerType(serverName);

        pluginPackageService.savePluginPackage(file, serverType);

        return ResponseUtil.buildResponse("성공적으로 저장되었습니다.", HttpStatus.CREATED);
    }
    
}
