package com.semi.lynk.function.approval_system.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DraftDTO {
    private Long draftNo;
    private String employeeNo;
    private String draftTitle;
    private LocalDateTime draftDate; // 작성일자
    private LocalDateTime draftCompletionTime; // 결재일자
    private int draftRetentionSchedule; // 보존기한
    private int draftState; // 결재상태 0미확인, 1확인, 2결재, 8임시저장, 9반려
    private Integer draftCost; // 시행금액
    private int draftLastStep; // 결재 최종 단계
    private int draftCurrentStep; // 현재 결재 단계
    private String draftMemo;
    private String employeeName;
    private int approvalState;
}
