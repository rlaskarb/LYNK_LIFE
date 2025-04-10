package com.semi.lynk.function.human.model.calendar;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ApprovalDTO {

    private int id;     // 사번
    private int draftNo; // 기안 번호

    // 결재 상태(0 미확인 / 1 확인 / 2 결재 / 9 반려)
    private int approvalState;
    private int approvalStep; // 결재 단계
    private int approvalPosible; // 결재 가능 (0 가능 / 1 대기 / 2 참조)

    private LocalDateTime approvalCompletionTime; // 결재 완료 일시
    private String approvalMemo;                   // 메모
}
