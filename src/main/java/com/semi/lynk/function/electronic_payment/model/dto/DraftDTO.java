package com.semi.lynk.function.electronic_payment.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DraftDTO {
    private int draftNo; // 문서번호
    private LocalDate draftDate; // 품위일
    private int implementationAmount; // 시행금액
    private String retentionPeriod; // 보존기간
    private String draftTitle; // 기안제목
    private String draftMemo; // 내용
    private int draftStatus; // 결재상태
    private String employeeNo; // 사번
    private String employeeName; // 이름  사원파트에서 가져옴
    private String departmentName; // 부서이름  부서파트 가져옴
    private int authOfDocument ; // 기안승인권한여부 권한파트에서 가져옴
}