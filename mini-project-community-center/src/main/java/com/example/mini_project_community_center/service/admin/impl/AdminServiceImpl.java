package com.example.mini_project_community_center.service.role.impl;

import com.example.mini_project_community_center.common.enums.error.ErrorCode;
import com.example.mini_project_community_center.common.enums.user.RoleType;
import com.example.mini_project_community_center.dto.ResponseDto;
import com.example.mini_project_community_center.dto.role.RoleRequestDto;
import com.example.mini_project_community_center.dto.user.response.UserListItemResponse;
import com.example.mini_project_community_center.entity.user.User;
import com.example.mini_project_community_center.exception.BusinessException;
import com.example.mini_project_community_center.repository.user.UserRepository;
import com.example.mini_project_community_center.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    private final UserRepository userRepository;

    @Override
    public ResponseDto<UserListItemResponse> updateRole(Long userId, RoleRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        RoleType newRole = dto.role();

        if (user.getRole() != newRole) {
            return ResponseDto.success("이미 동일한 역할을 가지고 있습니다.", UserListItemResponse.from(user));
        }

        user.changeRole(newRole);

        UserListItemResponse data = UserListItemResponse.from(user);

        return ResponseDto.success("권한이 성공적으로 변경되었습니다.", data);

    }

}
