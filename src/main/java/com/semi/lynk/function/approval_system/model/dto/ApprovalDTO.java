package com.semi.lynk.function.approval_system.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalDTO {
    private Long draftNo;   // 기안번호
    private String employeeNo;  // 내 사번(사인할 사람)
    private int approvalState; // 결재상태 0미확인, 1확인, 2결재, 9반려
    private int approvalStep;  // 나의 결재단계
    private int approvalPosible;   // 결재가능 0가능, 1대기, 2참조
    private LocalDateTime approvalCompletionTime;   // 사인한 시각
    private String approvalMemo;
    private String position; // human_resource, 직책 left join
    private String employeeName; // 내 이름 employee
    private String departmentName; // 내 부서이름 department left join
}
