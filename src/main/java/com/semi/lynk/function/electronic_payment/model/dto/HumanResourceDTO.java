package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class HumanResourceDTO {
    private int incentive; //인센티브
    private int workAllowance; // 수당
    private int totalLeave; // 연차발생일수
    private int usedLeave; //사용연차
    private String position; // 직책
    private String mobileNo; // 휴대전화
    private String address; // 주소
    private String ssn; // 주민번호
    private Date weddingDate; //결혼여부
    private Date joinDate; // 입사일자
    private Date resignationDate; // 퇴사일자
    private String nation; // 국적
    private String employeementStatus; // 고용구분
    private String bankOfSalary; // 급여은행
    private String bankAccountOfSalary; //급여계좌
    private String employeeNo; // 사번
    private String basicSalary; // 기본급
}
