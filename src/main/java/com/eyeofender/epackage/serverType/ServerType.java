package com.eyeofender.epackage.serverType;

import com.eyeofender.epackage.plugin.PluginPackage;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ServerType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String version;

    @OneToMany(mappedBy = "serverType")
    private List<PluginPackage> pluginPackages = new ArrayList<>();

    @Builder
    public ServerType(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public void addPackage(PluginPackage pluginPackage) {

        if(this.pluginPackages == null) {
            this.pluginPackages = new ArrayList<>();
        }

        this.pluginPackages.add(pluginPackage);
        pluginPackage.setServerType(this);
    }

}
