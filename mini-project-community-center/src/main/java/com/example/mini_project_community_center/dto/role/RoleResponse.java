package com.example.mini_project_community_center.dto.role;

import com.example.mini_project_community_center.common.enums.user.RoleStatus;
import com.example.mini_project_community_center.common.enums.user.RoleType;

public record RoleResponse(
        RoleType role,
        RoleStatus status
) {}
