package com.example.mini_project_community_center.dto.user.response;

import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.entity.course.CourseInstructor;
import com.example.mini_project_community_center.entity.user.User;

public record UserListItemResponseDto(
   Long id,
   String name,
   String loginId,
   String email,
   String phone,
   RoleType role
) {
    public static UserListItemResponseDto from(User user) {

        return new UserListItemResponseDto(
                user.getId(),
                user.getName(),
                user.getLoginId(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    public static UserListItemResponseDto fromCourseInstructor(CourseInstructor instructor) {

        return new UserListItemResponseDto(
                instructor.getInstructor().getId(),
                instructor.getInstructor().getName(),
                instructor.getInstructor().getLoginId(),
                instructor.getInstructor().getEmail(),
                instructor.getInstructor().getPhone(),
                instructor.getInstructor().getRole()
        );
    }
}
