package com.semi.lynk.function.human.model.calendar;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ScheduleDTO { // 일정 DTO

    private int id; // 사번
//    private int scheduleBound; // 일정 범위 -> 얘도 이제 안 씀
//    private LocalDateTime scheduleDate; // 일시 -> 얘 이제 안 씀

    private LocalDateTime scheduleStartDate;   // 일정 시작일
    private LocalDateTime scheduleEndDate;      // 일정 종료일

    private int scheduleType; // 일정 구분 (0 본인일정 / 1부서일정 / 2 전사일정)
    private String scheduleNote; // 내용

}
