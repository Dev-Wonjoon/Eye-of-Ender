package com.eyeofender.epackage.plugin;

import com.eyeofender.epackage.serverType.ServerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PluginPackageRepository extends JpaRepository<PluginPackage, Long> {
    Optional<PluginPackage> findTopByServerTypeOrderByCreatedAtDesc(ServerType serverType);
    Optional<PluginPackage> findByName(String pluginPackageName);
}
