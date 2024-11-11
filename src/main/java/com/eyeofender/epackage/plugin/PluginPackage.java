package com.eyeofender.epackage.plugin;

import com.eyeofender.epackage.serverType.ServerType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class PluginPackage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ServerType serverType;

    @Builder
    public PluginPackage(String name, String path, LocalDateTime createdAt, ServerType serverType) {
        this.name = name;
        this.path = path;
        this.createdAt = createdAt;
        this.serverType = serverType;
    }

}
