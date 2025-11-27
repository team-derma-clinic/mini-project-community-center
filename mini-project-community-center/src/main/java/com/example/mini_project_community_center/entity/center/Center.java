package com.example.mini_project_community_center.entity.center;

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

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "is_active")
    private boolean isActive;

    public static Center create(String name, String address, BigDecimal latitude, BigDecimal longitude, String phone) {
        Center center = new Center();
        center.name = name;
        center.address = address;
        center.latitude = latitude;
        center.longitude = longitude;
        center.phone = phone;
        return center;
    }

    public void update(String name, String address, BigDecimal latitude, BigDecimal longitude, String phone) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;

        validateCoordinates();
    }

    public void changeAddress(String newAddress) {
        this.address = newAddress;
    }

    public void changePhone(String newPhone) {
        this.phone = newPhone;
    }

    public void changeCoordinates(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        validateCoordinates();;
    }

    private void validateCoordinates() {
        if(latitude != null) {
            double doubleLatitude = latitude.doubleValue();
            if(doubleLatitude < -90 || doubleLatitude > 90) {
                throw new IllegalArgumentException("위도는 -90 ~ 90 사이여야 합니다.");
            }
        }

        if(longitude != null) {
            double doubleLongitude = longitude.doubleValue();
            if(doubleLongitude < -180 || doubleLongitude > 180) {
                throw new IllegalArgumentException("경도 -180 ~ 180 사이여야 합니다.");
            }
        }
    }
}
