package com.example.mini_project_community_center.entity;

import com.example.mini_project_community_center.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "centers",
        indexes = {
                @Index(name = "idx_centers_geo", columnList = "latitude, longitude")
        })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Center extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "phone")
    private String phone;

    public static Center createCenter(String name, String address, BigDecimal latitude, BigDecimal longitude, String phone) {
        Center center = new Center();
        center.name = name;
        center.address = address;
        center.latitude = latitude;
        center.longitude = longitude;
        center.phone = phone;
        return center;
    }
}
