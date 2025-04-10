package com.semi.lynk.function.human.model.calendar;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class OverTimeApplicationDTO { // 뭘 쓸지 정해야 함.

//    private int id;                     // 담당자 사번
    private String name;                  // 담당자 이름

    private Date startOverDay;          // 시작일
    private LocalDateTime startOverTime; // 시작 시간

    private Date endOverDay;            // 종료일
    private LocalDateTime endOverTime;  //종료 시간
//
    private LocalDateTime workOff;    // 퇴근 시간
               // 기본 퇴근시간(18:00) + (종료시간 - 시작시간)
//
//    private LocalDateTime scheduleDate; // 일시

    private LocalDateTime scheduleStartDate;    // 일정 시작일
    private LocalDateTime scheduleEndDate;    // 일정 종료일
    private int scheduleType;           // 스케쥴 타입 (0 개인일정/1부서일정/2전사일정)
    private String scheduleNote;        // 스케쥴 메모 (사 유)
}
