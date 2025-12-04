package com.example.mini_project_community_center.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PageRequestDto(
        @NotNull(message = "page 입력은 필수입니다.")
        @Min(value = 0, message = "page 는 0 이상이어야 합니다.")
        Integer page,

        @NotNull(message = "size 입력은 필수입니다.")
        @Min(value = 1, message = "size는 1이상이어야 합니다.")
        @Max(value = 100, message = "size는 100이하여야 합니다.")
        Integer size,

        @Pattern(
                regexp = "^[a-zA-Z0-9_]+,(asc|desc)$",
                message = "sort 형식은 '필드명,asc' 또는 '필드명,desc' 형식이어야 합니다."
        )
        String sort
) {
    public PageRequestDto {
        if(page == null) page = 0;
        if(size == null) size = 10;
        if(sort == null || sort.isBlank()) sort = "createdAt,desc";
    }

    public Pageable toPageable() {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);

        return PageRequest.of(page, size, Sort.by(direction, sortField));
    }
}
