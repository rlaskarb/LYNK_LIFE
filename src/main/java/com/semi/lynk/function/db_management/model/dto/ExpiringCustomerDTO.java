package com.semi.lynk.function.db_management.model.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpiringCustomerDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractDate; // 계약일자
    private String contractMngNo; // 계약일련번호
    private String customerName; // 계약자명
    private String insuredName; // 피보험자명
    private String productName; // 상품명
    private String insuranceCompany; // 보험회사 코드
    private String contractNo;// 계약번호
    private String departmentName; // 부서명
    private String employeeName; // 설계사 이름
    private String employeeNo; // 설계사 사번
    private Integer contractDuration; // 계약기간
    private String expiringDate;
    private int insuranceCompanyCode; // 보험회사 코드
}

