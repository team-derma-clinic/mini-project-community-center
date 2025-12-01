package com.example.mini_project_community_center.service.system.impl;

import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.system.response.DatabaseHealthResponse;
import com.example.mini_project_community_center.dto.system.response.SystemHealthResponse;
import com.example.mini_project_community_center.dto.system.response.SystemInfoResponse;
import com.example.mini_project_community_center.service.system.SystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SystemServiceImpl implements SystemService {
    private final DataSource dataSource;
    private final Environment environment;
    private final Optional<BuildProperties> buildProperties;

    @Override
    public ResponseDto<SystemInfoResponse> getSystemInfo() {
        String version = buildProperties
                .map(BuildProperties::getVersion)
                .orElse("unknown");

        String name = buildProperties
                .map(BuildProperties::getName)
                .orElse("mini-project-community-center");

        String environmentName = environment.getActiveProfiles().length > 0
                ? String.join(",", environment.getActiveProfiles())
                : "default";

        SystemInfoResponse data = new SystemInfoResponse(
                version,
                name,
                "Community Center Management System",
                environmentName,
                buildProperties.map(bp -> bp.getTime().toString()).orElse("unknown"),
                getUptime()
        );

        return ResponseDto.success(data);
    }

    @Override
    public ResponseDto<SystemHealthResponse> getSystemHealth() {
        DatabaseHealthResponse databaseHealth = checkDatabaseHealth();

        String version = buildProperties
                .map(BuildProperties::getVersion)
                .orElse("unknown");

        SystemHealthResponse data = new SystemHealthResponse(
                "UP",
                Instant.now().toString(),
                version,
                databaseHealth
        );

        return ResponseDto.success(data);
    }

    private DatabaseHealthResponse checkDatabaseHealth() {
        try (Connection connection = dataSource.getConnection()) {
            long startTime = System.currentTimeMillis();
            boolean isValid = connection.isValid(2);
            long responseTime = System.currentTimeMillis() - startTime;

            return new DatabaseHealthResponse(
                    isValid ? "UP" : "DOWN",
                    responseTime
            );
        } catch (Exception e) {
            return new DatabaseHealthResponse("DOWN", -1L);
        }
    }

    private Long getUptime() { return ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
    }
}
