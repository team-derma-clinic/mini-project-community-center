package com.example.mini_project_community_center.dto.user.response;

import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.course.CourseInstructor;
import com.example.mini_project_community_center.entity.user.User;
import jakarta.persistence.EntityNotFoundException;

public record UserListItemResponse(
   Long id,
   String name,
   String loginId,
   String email,
   String phone,
   RoleType role
) {
    public static UserListItemResponse from(User user) {
        RoleType role = user.getUserRoles().stream()
                .map(r -> r.getRole().getName())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("권한이 존재하지 않습니다."));

        return new UserListItemResponse(
                user.getId(),
                user.getName(),
                user.getLoginId(),
                user.getEmail(),
                user.getPhone(),
                role
        );
    }

    public static UserListItemResponse fromCourseInstructor(CourseInstructor instructor) {
        RoleType role = instructor.getInstructor().getUserRoles().stream()
                .map(r -> r.getRole().getName())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("권한이 존재하지 않습니다."));

        return new UserListItemResponse(
                instructor.getInstructor().getId(),
                instructor.getInstructor().getName(),
                instructor.getInstructor().getLoginId(),
                instructor.getInstructor().getEmail(),
                instructor.getInstructor().getPhone(),
                role
        );
    }
}
