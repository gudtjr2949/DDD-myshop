package com.ddd.common.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {
    private String postalCode; // 우편번호
    private String state;      // 시/도
    private String city;       // 시/군/구
    private String district;   // 읍/면/동
    private String streetAddress; // 상세주소
}
