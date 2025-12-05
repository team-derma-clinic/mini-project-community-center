package com.example.mini_project_community_center.dto.map;

import java.util.List;

public record NaverReverseGeoResponse(
        Status status,
        List<Result> results
) {
    public record Status(
            int code,
            String name,
            String message
    ) {}
    public record Result(
            String name,
            Code code,
            Region region,
            Land land
    ) {}
    public record Code(
            String id,
            String type,
            String mappingId
    ) {}
    public record Region(
            Area area0,
            Area area1,
            Area area2,
            Area area3,
            Area area4
    ) {}
    public record Area(
            String name,
            Coordinate coords,
            String alias
    ) {}
    public record Coordinate(
            double centerX,
            double centerY
    ) {}
    public record Land(
            String type,
            String number1,
            String number2,
            Addition addition0,
            Addition addition1,
            Addition addition2,
            Addition addition3,
            Addition addition4,
            Coordinate coords
    ) {}
    public record Addition(
            String type,
            String value
    ) {}

    public String extractFullAddress() {
        Result result = results.get(0);

        String area1 = result.region().area1().name();
        String area2 = result.region().area2().name();
        String area3 = result.region().area3().name();

        String number1 = result.land().number1();
        String number2 = result.land().number2();

        return area1 + " " + area2 + " " + area3 + " " + number1 +
                (number2 != null && !number2.isEmpty() ? "-" + number2: "");
    }
}
