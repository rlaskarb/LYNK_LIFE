package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApproveDTO {
    private int approver; // 결재/반려
    private String authoMemo; // 메모
    private int draftNo; // 문서번호 (복합식별자 FK)
    private String employeeNo; // 사번 (복합식별자 FK)
}