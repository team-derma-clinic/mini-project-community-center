package com.example.mini_project_community_center.dto.user.request;

import com.example.mini_project_community_center.common.enums.RoleType;

public record UserListRequestDto (
        RoleType role
) {}
