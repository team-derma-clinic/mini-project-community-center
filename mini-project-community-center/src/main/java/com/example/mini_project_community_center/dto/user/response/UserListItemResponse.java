package com.example.mini_project_community_center.dto.user.response;

import com.example.mini_project_community_center.common.enums.RoleType;

public record UserListItemResponse(
   Long id,
   String name,
   String loginId,
   String email,
   String phone,
   RoleType role
) {}
