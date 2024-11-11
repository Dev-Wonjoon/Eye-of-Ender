package com.eyeofender.epackage.serverType;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerTypeRepository extends JpaRepository<ServerType, Long> {
    Optional<ServerType> findByName(String name);
}
