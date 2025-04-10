package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RegistHumDTO {

    private int id;                   // 사번

    private String employeementStatus; // 고용 여부
    private String address;             // 주소
    private String nation;              // 국가
    private String phoneNumber;         // 휴대 전화
    private String joinDate;              // 입사일자
    private String position;

}
