package com.eyeofender.epackage.serverType;

import com.eyeofender.response.GenericResponse;
import com.eyeofender.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/package/server")
public class ServerTypeController {

    private final ServerTypeService pluginManagerService;

    public ServerTypeController(ServerTypeService pluginManagerService) {
        this.pluginManagerService = pluginManagerService;
    }

    @PostMapping("/create")
    public ResponseEntity<GenericResponse> createServerType(
            @Valid @RequestBody ServerTypeRequest serverTypeRequest) {
        if(pluginManagerService.checkServerType(serverTypeRequest.serverName())) {
            return ResponseUtil.buildResponse("이미 존재하는 서버 이름입니다.", HttpStatus.BAD_REQUEST);
        }

        return pluginManagerService.createServerType(
                serverTypeRequest.serverName(),
                serverTypeRequest.version(),
                serverTypeRequest.pluginPackage());
    }

    @GetMapping("/{serverName}/latest")
    public ResponseEntity<GenericResponse> getLatestServerPackage(
            @PathVariable("serverName") String serverName) {
        if(!pluginManagerService.checkServerType(serverName)) {
            return ResponseUtil.buildResponse("존재하지 않는 서버 이름입니다.", HttpStatus.BAD_REQUEST);
        }
        ServerType serverType = pluginManagerService.getServerType(serverName);

        return pluginManagerService.getLatestServerPackage(serverType);
    }
}
