package com.semi.lynk.function.db_management.model.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InquiryDTO {
    private int contractMngNo; // 계약일련번호
    private  String customerName; // 고객명
    private String insuredName; // 피보험자
    private String insuranceCompany; // 보험회사명 코드
    private String productName; // 상품
    private String contractNo; // 증권번호
    private String employeeName; // 설계사 이름
    private Date contractDate; // 계약일자
    private String contractName; // 계약자명
    private String departmentName; // 부서명
    private String employeeNo; // 설계사 사번
}
