package com.eyeofender.epackage.serverType;

import com.eyeofender.epackage.plugin.PluginPackage;
import jakarta.validation.constraints.NotEmpty;


public record ServerTypeRequest(
        @NotEmpty
        String serverName,
        @NotEmpty
        String version,
        PluginPackage pluginPackage
) {
}
