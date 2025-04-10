package com.semi.lynk.function.human.model.calendar;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommuteDTO { // 출퇴근 이력 DTO

    private int id; // 사번
    private LocalDate workDate; // 출근(근무)일자
    private LocalDateTime workOn; // 출근 시간
    private LocalDateTime workOff; // 퇴근 시간
    private LocalDateTime workOnOutside; // 외근 시작시간
    private LocalDateTime workOffOutside; // 외근 종료시간
     }
