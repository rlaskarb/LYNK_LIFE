package com.semi.lynk.function.human.model.calendar;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class DraftshDTO {

    private int draftNo;                    // 기안 번호
    private int employeeNo;                 // 사번

    private String draftTitle;              // 기안 제목
    private LocalDateTime draftDate;        // 기안 일자
    private int draftState;  // 기안 상태 (0 미확인 / 1 확인 / 2 결재 / 9 반려)
    private int draftCurrentStep;       // 현재 결재 단계
    private int draftLastStep;          // 최종 결재 단계

    // 아래는 null 허용
    private LocalDateTime draftCompletionTime; // 결재 일자
    private String draftMemo;       // 기안 내용

    private int draftCost;                  // 시행 금액
    private int draftRetentionSchedule; // 문서 보존 기한
}
