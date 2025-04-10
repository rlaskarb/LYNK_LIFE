package com.semi.lynk.function.db_management.model.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractDetailsDTO {
    private String employeeName; // 이름
    private String contractNo; // 계약번호
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractDate; // 계약일자
    private int contractDuration; // 계약기간
    private int eachPayment; // 납입금액
    private String basicPayWith; // 기본결제수단
    private int paymentTerm; //납입주기
    private int paymentDay; //납입예정일자
    private  String customerName; // 고객명
    private  String customerSsn; // 주민번호
    private  String customerMobile; // 연락처
    private String insuredName; // 피보험자
    private String insuredSsn; // 피보험자 주민번호
    private  String otherMatters; // 기타사항
    private  String productNo; // 상품번호 FK
    private String insuranceCompany; // 보험회사 코드
    private String productName; // 상품
    private  Date lastReformDate; // 최종 수정일자
    private String lastInseminatee; //최종 수정자
    private  String customerEmail; // 이메일


}
