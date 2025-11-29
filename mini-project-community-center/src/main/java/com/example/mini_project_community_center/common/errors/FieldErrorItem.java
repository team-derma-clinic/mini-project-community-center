package com.example.mini_project_community_center.common.errors;

public record FieldErrorItem
        (
                String field,
                String message,
                String rejected
        ) {}
