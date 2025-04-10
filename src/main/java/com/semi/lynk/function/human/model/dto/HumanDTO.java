package com.semi.lynk.function.human.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class HumanDTO {

    private int id;                    // 사번
    private double salary;            // 기본급
    private double incentive;           // 인센티브
    private String ssn;                  // 주민 번호
    private String weddingDate;       // 결혼 여부
    private String dayOfResignation;    // 퇴사일자 (null 허용)
    private String salaryBank;          // 급여 은행
    private String salaryAccount;       // 급여 계좌
    private String text;                // 비고 (null 허용)

    // 인사 등록
    private String nation;              // 국가
    private String position;            // 직책
    private String phoneNumber;         // 휴대 전화
    private String address;             // 주소
    private String employeementStatus;    // 고용 구분
    private String joinDate;              // 입사일자

    // 캘린더
    private float totalLeave;          // 연차 발생 일수
    private float usedLeave;            // 사용 연차

}
