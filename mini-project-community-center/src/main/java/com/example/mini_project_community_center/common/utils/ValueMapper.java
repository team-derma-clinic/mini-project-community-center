package com.example.mini_project_community_center.common.utils;

import java.math.BigDecimal;

public class ValueMapper {
    public static BigDecimal toBigDecimalOrNull(String value) {
        if(value == null || value.isBlank()) {
            return null;
        }
        return new BigDecimal(value);
    }

    public static Double toDoubleOrNull(BigDecimal value) {
        if(value == null) return null;
        return value.doubleValue();
    }
}
